/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.utils;

import entities.Equivalencia;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;



/**
 *
 * @author abc
 */

@ViewScoped
@ManagedBean
public class ReportBean {

    
    private JasperPrint jasperPrint;
    
    
        
        public ReportBean() {
    }
        
        public void reporte(Collection listaAuxEquivalencias,Map parametros)throws JRException{
           
        String reportPath=FacesContext.getCurrentInstance().getExternalContext().getRealPath("/resources/reportes/reportEquivalencias.jasper");
        JRBeanCollectionDataSource beanCollectionDataSource=new JRBeanCollectionDataSource(listaAuxEquivalencias);
            
        jasperPrint=JasperFillManager.fillReport(reportPath, parametros, beanCollectionDataSource);
        
    }
    
    public void PDF(ActionEvent actionEvent,Collection listaAuxEquivalencias,Map parametros)throws JRException, IOException{
        reporte(listaAuxEquivalencias,parametros);
        HttpServletResponse httpServletResponse=(HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();  
      httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.pdf");  
       ServletOutputStream servletOutputStream=httpServletResponse.getOutputStream(); 
       
       JasperExportManager.exportReportToPdfStream(jasperPrint, servletOutputStream);  
       FacesContext.getCurrentInstance().responseComplete(); 
    }
    
     public ArrayList<EquivalenciaJasper> crearEquivalenciasJasper(ArrayList<Equivalencia> listaAuxEquivalencias){
        
        ArrayList<EquivalenciaJasper> listaEquivalenciasJasper=new ArrayList<EquivalenciaJasper>();
        
        for(Equivalencia e:listaAuxEquivalencias){
            
            EquivalenciaJasper ej=new EquivalenciaJasper(e);
            listaEquivalenciasJasper.add(ej);
        }
        
     return listaEquivalenciasJasper;
    }
    
    
    
    
    
    
    
}
