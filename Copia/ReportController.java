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

}
