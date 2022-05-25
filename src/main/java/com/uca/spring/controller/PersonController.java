package com.uca.spring.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.bouncycastle.asn1.x509.sigi.PersonalData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.uca.spring.jqgrid.JqgridFilter;
import com.uca.spring.jqgrid.JqgridResponse;
import com.uca.spring.model.Municipio;
import com.uca.spring.model.Person;
import com.uca.spring.model.PersonDTO;
import com.uca.spring.poi.LayOutDynamic;
import com.uca.spring.poi.Writer;
import com.uca.spring.repository.PersonDTORepository;
import com.uca.spring.repository.PersonRepository;
import com.uca.spring.util.CboFilter;


@Controller
@RequestMapping("/")
public class PersonController {

  
  @Autowired
  PersonDTORepository personDTORepository;
  @Autowired
  PersonRepository personRepository;
  
  /**
   * /** Index jsp Controller
   *
   * @return ModelAndView
   * @author Wsalazar
   * @version 1.0
   */
  @RequestMapping("/indexPerson")
  public ModelAndView indexPerson() {
    ModelAndView mv = new ModelAndView();
    
    /*Person personTest = personRepository.getById(2);
    
    if(personTest.getIdPerson()==10){
    	personTest.setNombre("walter");
    	personRepository.save(personTest);
    }
    Page<PersonDTO> list = personDTORepository.getPersonsDto( PageRequest.of(0, 100));
    List<PersonDTO> listNative = personDTORepository.getPersons();
    
    List<Person> personsName = personRepository.findByNombre("walter oswaldo");
    Integer countPerson =personRepository.countPerson();
    List<Person> persons = personRepository.getPerson(2);
    //personRepository.deletePerson(3);
    String namePerson = personRepository.getName(2);*/
    
    
    mv.setViewName("person/index.jsp");
    return mv;
  }

  
  
  
  /**
   * Controlador para obtener la data y devolverla a un jqgrid Paginado por medio de Ajax
   * @return JqgridResponse lleno con la data paginada
   * @Param filters trae las columnas que se utilizaran para filtrar la query
   * @Param page la pagina actual del jqgrid
   * @Param rows la cantidad de filas a mostrar en la paginacion
   * @author walter_salazarg@hotmail.com
   * @version 1.0
   */
   @RequestMapping(value = "/gridPerson", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
   public @ResponseBody JqgridResponse<Person> gridPerson(
       @RequestParam(value = "filters", required = false) String filters,
       @RequestParam(value = "page", required = false) Integer page,
       @RequestParam(value = "rows", required = false) Integer rows,
       @RequestParam(value = "sidx", required = false) String sidx,
       @RequestParam(value = "sord", required = false) String sord 
   ) {
   
	   
	   Page<Person> list = personRepository.findByFilters(
			   PageRequest.of(page - 1, rows, Sort.by(sord.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sidx))
	            ,JqgridFilter.getFieldInteger(filters, "idPerson") 
	            ,JqgridFilter.getField(filters, "correo") 
	            ,JqgridFilter.getField(filters, "direccion") 
	            ,JqgridFilter.getField(filters, "especialidad") 
	            ,JqgridFilter.getField(filters, "fechaCreacion") 
	            ,JqgridFilter.getField(filters, "nombre") 
	            ,JqgridFilter.getField(filters, "telefono") 
	            ,JqgridFilter.getFieldInteger(filters, "departamento.idDepartamento") 
	            ,JqgridFilter.getFieldInteger(filters, "municipio.idMunicipio") 
	        );

	       JqgridResponse<Person> jqgridPerson = new JqgridResponse<Person>();
	       return jqgridPerson.jGridFill(list, page, rows);
   }
   
   /**
    * Controlador para exportar a excel
    * @return void 
    * @Param filters trae las columnas que se utilizaran para filtrar la query
    * @author walter_salazarg@hotmail.com
    * @version 1.0
    */
    @RequestMapping(value = "/exportPerson", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void exportPerson(
        HttpServletResponse response,@RequestParam(value = "filters", required = false) String filters
    ) {
    
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("libro");
        
        List<String> header = new ArrayList<String>();
        header.add("idPersona"); 
        header.add("correo"); 
        header.add("direccion"); 
        header.add("especialidad"); 
        header.add("fechaCreacion"); 
        header.add("nombre"); 
        header.add("telefono"); 
        header.add("departamentoDescriptionDelegate"); 
        header.add("municipioDescriptionDelegate"); 
        
        LayOutDynamic.buildReport(worksheet, "Person", header);
        
        List<Object[]> list = personRepository.findByFilters(
           JqgridFilter.getFieldInteger(filters, "idPersona") 
            ,JqgridFilter.getField(filters, "correo") 
            ,JqgridFilter.getField(filters, "direccion") 
            ,JqgridFilter.getField(filters, "especialidad") 
            ,JqgridFilter.getField(filters, "fechaCreacion") 
            ,JqgridFilter.getField(filters, "nombre") 
            ,JqgridFilter.getField(filters, "telefono") 
            ,JqgridFilter.getFieldInteger(filters, "departamentoDescriptionDelegate") 
            ,JqgridFilter.getFieldInteger(filters, "municipioDescriptionDelegate") 
        );
        

       LayOutDynamic.fillReport(worksheet, header.size(),list);
       String fileName = "Person.xls";
       response.setHeader("Content-Disposition", "inline; filename=" + fileName);
       response.setContentType("application/vnd.ms-excel");
       Writer.write(response, worksheet);
    }
    
  

    /**
    * Controlador para guardar por medio de Ajax
    * @return String pero se le coloca null para indicar que no hubo ningun error
    * @Param Modelo que vendra lleno para guardarlo directamente en la base de datos
    * @author walter_salazarg@hotmail.com
    * @version 1.0
    */
    @RequestMapping(value = "/savePerson", method = RequestMethod.POST)
    public @ResponseBody String savePerson(@ModelAttribute("Person") @Validated Person person ) {
    	
         personRepository.save(person);
         return null;
     }
    
    /**
    * Controlador para eliminar por medio de Ajax
    * @return String pero se le coloca null para indicar que no hubo ningun error
    * @Param Modelo que vendra lleno para eliminarlo directamente en la base de datos, se elimina por medio del @Id
    * @author walter_salazarg@hotmail.com
    * @version 1.0
    */
    @RequestMapping(value = "/deletePerson", method = RequestMethod.DELETE)
    public @ResponseBody String deletePerson(@ModelAttribute("Person")  Person person ) {
         personRepository.delete(person);
         return null;
     }
    
    @RequestMapping(value = {"/cbofilterPerson"}, method = RequestMethod.GET,
    	      produces = "application/json;charset=UTF-8")
    	  public @ResponseBody List<CboFilter> cbofilterMunicipioByDep() {
    	    
    	    List<Person> persons = personRepository.findAll();
    	    List<CboFilter> response = new ArrayList<>();
    	    for (int i = 0; i < persons.size(); i++) {
    	      response.add(new CboFilter(Integer.toString(persons.get(i).getIdPerson()), persons.get(i).getNombre()));
    	    }
    	    return response;
    	  }
    	  
  
}
