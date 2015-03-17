/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.utils;

import entities.Asignatura;
import entities.Equivalencia;
import entities.MiembroGrupoAsignaturaA;
import entities.MiembroGrupoAsignaturaB;
import java.util.ArrayList;
import java.util.Iterator;




public class EquivalenciaJasper {
 
    private Integer idequivalencia;
    private ArrayList<Asignatura> asignaturasA=new ArrayList<Asignatura>();
    private ArrayList<Asignatura> asignaturasB=new ArrayList<Asignatura>();
    
    public EquivalenciaJasper(Equivalencia e){
        
    this.idequivalencia=e.getIdequivalencia();
    Iterator i=e.getMiembroGrupoAsignaturaAs().iterator();
    while(i.hasNext()){
      MiembroGrupoAsignaturaA mA=(MiembroGrupoAsignaturaA)i.next();
      asignaturasA.add(mA.getAsignatura());
    }
    
      Iterator j=e.getMiembroGrupoAsignaturaBs().iterator();
    while(j.hasNext()){
      MiembroGrupoAsignaturaB mB=(MiembroGrupoAsignaturaB)j.next();
      asignaturasB.add(mB.getAsignatura());
        
        
    }
    
        
    
    }

    public Integer getIdequivalencia() {
        return idequivalencia;
    }

    public void setIdequivalencia(Integer idequivalencia) {
        this.idequivalencia = idequivalencia;
    }

    public ArrayList<Asignatura> getAsignaturasA() {
        return asignaturasA;
    }

    public void setAsignaturasA(ArrayList<Asignatura> asignaturasA) {
        this.asignaturasA = asignaturasA;
    }

    public ArrayList<Asignatura> getAsignaturasB() {
        return asignaturasB;
    }

    public void setAsignaturasB(ArrayList<Asignatura> asignaturasB) {
        this.asignaturasB = asignaturasB;
    }

    
    
    
}
