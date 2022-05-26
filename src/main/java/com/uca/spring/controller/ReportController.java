package com.uca.spring.controller;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.uca.spring.jasper.JasperParameters;
import com.uca.spring.jasper.JasperReports;

@Controller
@RequestMapping("/")
public class ReportController {

    @Autowired
    JasperReports jasperReports;

    @Autowired
    ServletContext servletContext = null;

    @RequestMapping(value = {"/indexReportPersona"}, method = RequestMethod.GET)
    public ModelAndView indexReportSolicitud(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.setViewName("reports/reportPerson.jsp");
        return model;
    }
    
    @RequestMapping(value = {"/indexReportVehiculo"}, method = RequestMethod.GET)
    public ModelAndView indexReportVehiculoSolicitud(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.setViewName("reports/reportVehiculo.jsp");
        return model;
    }
    

    @RequestMapping("/printReportPerson")
    public ModelAndView imprimirReportAgenda(
            @RequestParam(required = false, value = "idperson",defaultValue = "") Integer idPerson,
            @RequestParam(required = false, value = "fechainicio",defaultValue = "") String fechainicio,
            @RequestParam(required = false, value = "fechafin",defaultValue = "") String fechafin,
            @RequestParam(required = false, value = "iddepartamento",defaultValue = "") Integer iddepartamento,
            @RequestParam(required = false, value = "idmunicipio",defaultValue = "") Integer idmunicipio,
            @RequestParam(required = false, value = "telefono",defaultValue = "") String telefono,
            @RequestParam(required = false, value = "typeexport",defaultValue = "") String typeexport,
            HttpServletResponse response, HttpServletRequest request) {

        try {

            JasperParameters jasperParameters = new JasperParameters();
            jasperParameters.addParameter("P_ID_PERSONA", idPerson);
            jasperParameters.addParameter("P_FECHA_INICIO", fechainicio);
            jasperParameters.addParameter("P_FECHA_FIN", fechafin);
            jasperParameters.addParameter("P_ID_DEPARTAMENTO", iddepartamento);
            jasperParameters.addParameter("P_ID_MUNICIPIO", idmunicipio);
            jasperParameters.addParameter("P_TELEFONO", telefono);
            

            if (typeexport.equals("pdf")) {
                jasperParameters.setType(JasperParameters.PDF_CONTENT_TYPE);
            } else if (typeexport.equals("excel")) {
                jasperParameters.setType(JasperParameters.EXCEL_CONTENT_TYPE);
            }
            
            jasperParameters.setFolder("person");
            jasperParameters.setTitle("Personas");
            jasperParameters.setFileName("reportPerson.jasper");

            jasperParameters.setType(JasperParameters.PDF_CONTENT_TYPE);

            jasperReports.executeReport(response, jasperParameters);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null;
    }
    
    @RequestMapping("/printReportVehiculo")
    public ModelAndView imprimirReportVehiculo(
            @RequestParam(required = false, value = "idcarro",defaultValue = "") Integer idCarro,
            @RequestParam(required = false, value = "marca",defaultValue = "") String marca,
            @RequestParam(required = false, value = "placa",defaultValue = "") String placa,
            @RequestParam(required = false, value = "color",defaultValue = "") Integer color,
            @RequestParam(required = false, value = "modelo",defaultValue = "") Integer modelo,
            @RequestParam(required = false, value = "typeexport",defaultValue = "") String typeexport,
            HttpServletResponse response, HttpServletRequest request) {

        try {

            JasperParameters jasperParameters = new JasperParameters();
            jasperParameters.addParameter("P_ID_CARRO", idCarro);
            jasperParameters.addParameter("P_MARCA", marca);
            jasperParameters.addParameter("P_PLACA", placa);
            jasperParameters.addParameter("P_COLOR", color);
            jasperParameters.addParameter("P_MODELO", modelo);            
            

            if (typeexport.equals("pdf")) {
                jasperParameters.setType(JasperParameters.PDF_CONTENT_TYPE);
            } else if (typeexport.equals("excel")) {
                jasperParameters.setType(JasperParameters.EXCEL_CONTENT_TYPE);
            }
            
            jasperParameters.setFolder("vehiculo");
            jasperParameters.setTitle("Vehiculos");
            jasperParameters.setFileName("reportVehiculo.jasper");

            jasperParameters.setType(JasperParameters.PDF_CONTENT_TYPE);

            jasperReports.executeReport(response, jasperParameters);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null;
    }

}
