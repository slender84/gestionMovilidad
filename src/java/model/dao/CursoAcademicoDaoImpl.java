/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import entities.Cursoacademico;
import org.springframework.stereotype.Repository;

@Repository("cursoacademicoDao")
public class CursoAcademicoDaoImpl extends GenericDaoHibernate<Cursoacademico, String> implements CursoAcademicoDao{
    
}
