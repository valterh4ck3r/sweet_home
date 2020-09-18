/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdsc;

import static javax.persistence.PersistenceContextType.TRANSACTION;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author masc0
 */
@Stateless
@LocalBean
public class UsuarioBean {

    @PersistenceContext(name = "pdsc", type = TRANSACTION)
    private EntityManager entityManager;

    public void salvar(Usuario usuario) {
        entityManager.persist(usuario);
    }
}
