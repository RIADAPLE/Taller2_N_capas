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
import com.uca.spring.model.Vehiculo;
import com.uca.spring.poi.LayOutDynamic;
import com.uca.spring.poi.Writer;
import com.uca.spring.repository.VehiculoRepository;
import com.uca.spring.util.CboFilter;


@Controller
@RequestMapping("/")
public class VehiculoController {

  
  @Autowired
  VehiculoRepository vehiculoRepository;
  
  /**
   * /** Index jsp Controller
   *
   * @return ModelAndView
   * @author Wsalazar
   * @version 1.0
   */
  @RequestMapping("/indexVehiculo")
  public ModelAndView indexVehiculo() {
    ModelAndView mv = new ModelAndView();  
    
    mv.setViewName("vehiculo/index.jsp");
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
   @RequestMapping(value = "/gridVehiculo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
   public @ResponseBody JqgridResponse<Vehiculo> gridVehiculo(
       @RequestParam(value = "filters", required = false) String filters,
       @RequestParam(value = "page", required = false) Integer page,
       @RequestParam(value = "rows", required = false) Integer rows,
       @RequestParam(value = "sidx", required = false) String sidx,
       @RequestParam(value = "sord", required = false) String sord 
   ) {
   
	   
	   Page<Vehiculo> list = vehiculoRepository.findByFilters(
			   PageRequest.of(page - 1, rows, Sort.by(sord.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sidx))
	            ,JqgridFilter.getFieldInteger(filters, "idCarro") 
	            ,JqgridFilter.getField(filters, "marca") 
	            ,JqgridFilter.getField(filters, "placa") 
	            ,JqgridFilter.getField(filters, "color") 
	            ,JqgridFilter.getField(filters, "modelo") 	            	            
	        );

	       JqgridResponse<Vehiculo> jqgridVehiculo = new JqgridResponse<Vehiculo>();
	       return jqgridVehiculo.jGridFill(list, page, rows);
   }
   
   /**
    * Controlador para exportar a excel
    * @return void 
    * @Param filters trae las columnas que se utilizaran para filtrar la query
    * @author walter_salazarg@hotmail.com
    * @version 1.0
    */
    @RequestMapping(value = "/exportVehiculo", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void exportVehiculo(
        HttpServletResponse response,@RequestParam(value = "filters", required = false) String filters
    ) {
    
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet worksheet = workbook.createSheet("libro");
        
        List<String> header = new ArrayList<String>();
        header.add("idCarro"); 
        header.add("marca"); 
        header.add("placa"); 
        header.add("color"); 
        header.add("modelo");         
        
        LayOutDynamic.buildReport(worksheet, "Vehiculo", header);
        
        List<Object[]> list = vehiculoRepository.findByFilters(           
           JqgridFilter.getFieldInteger(filters, "idCarro") 
           ,JqgridFilter.getField(filters, "marca") 
           ,JqgridFilter.getField(filters, "placa") 
           ,JqgridFilter.getField(filters, "color") 
           ,JqgridFilter.getField(filters, "modelo") 
        );
        

       LayOutDynamic.fillReport(worksheet, header.size(),list);
       String fileName = "Vehiculo.xls";
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
    @RequestMapping(value = "/saveVehiculo", method = RequestMethod.POST)
    public @ResponseBody String saveVehiculo(@ModelAttribute("Vehiculo") @Validated Vehiculo vehiculo ) {
    	
         vehiculoRepository.save(vehiculo);
         return null;
     }
    
    /**
    * Controlador para eliminar por medio de Ajax
    * @return String pero se le coloca null para indicar que no hubo ningun error
    * @Param Modelo que vendra lleno para eliminarlo directamente en la base de datos, se elimina por medio del @Id
    * @author walter_salazarg@hotmail.com
    * @version 1.0
    */
    @RequestMapping(value = "/deleteVehiculo", method = RequestMethod.DELETE)
    public @ResponseBody String deleteVehiculo(@ModelAttribute("Vehiculo")  Vehiculo vehiculo ) {
         vehiculoRepository.delete(vehiculo);
         return null;
     }
    
    @RequestMapping(value = {"/cbofilterVehiculo"}, method = RequestMethod.GET,
    	      produces = "application/json;charset=UTF-8")
    	  public @ResponseBody List<CboFilter> vehiculofindIDByMarca() {
    	    
    	    List<Vehiculo> vehiculo = vehiculoRepository.findAll();
    	    List<CboFilter> response = new ArrayList<>();
    	    for (int i = 0; i < vehiculo.size(); i++) {
    	      response.add(new CboFilter(Integer.toString(vehiculo.get(i).getIdCarro()), vehiculo.get(i).getMarca()));
    	    }
    	    return response;
    	  }
}
    	  
  