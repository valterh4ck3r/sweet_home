///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package pdsc;
//
//import static javax.persistence.PersistenceContextType.TRANSACTION;
//
//import java.util.ArrayList;
//
//import javax.ejb.Stateless;
//import javax.ejb.LocalBean;
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import javax.persistence.TypedQuery;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.Root;
//
///**
// *
// * @author masc0
// */
//@Stateless
//@LocalBean
//public class UsuarioBean {
//
//    @PersistenceContext(name = "pdsc", type = TRANSACTION)
//    private EntityManager entityManager;
//
//    public void salvar(Usuario usuario) {
//        entityManager.persist(usuario);
//    }
//    
//    ArrayList usersList;
//
//    public ArrayList list(){
//        try{
//        	CriteriaBuilder cb = entityManager.getCriteriaBuilder();
//            CriteriaQuery<ArrayList> cq = cb.createQuery(ArrayList.class);
//            Root<ArrayList> rootEntry = cq.from(ArrayList.class);
//            CriteriaQuery<ArrayList> all = cq.select(rootEntry);
//            TypedQuery<ArrayList> allQuery = entityManager.createQuery(all);
//            return (ArrayList) allQuery.getResultList();
//        }catch(Exception e){
//            System.out.println(e);
//        }
//        return usersList;
//    }
//    // Used to save user record
//    public String save(){
//        int result = 0;
//        try{
//        	entityManager.persist(new Usuario());
//        }catch(Exception e){
//            System.out.println(e);
//        }
//        if(result !=0)
//            return "index.xhtml?faces-redirect=true";
//        else return "create.xhtml?faces-redirect=true";
//    }
//    // Used to fetch record to update
//    public String edit(int id){
//        User user = null;
//        System.out.println(id);
//        try{
//        	// Obter Imovel pelo ID
//        }catch(Exception e){
//            System.out.println(e);
//        }       
//        return "/edit.xhtml?faces-redirect=true";
//    }
//    // Used to update user record
//    public String update(User u){
//        //int result = 0;
//        try{
//
//        	// Atualizar Usuario
//        }catch(Exception e){
//            System.out.println();
//        }
//        return "/index.xhtml?faces-redirect=true";      
//    }
//    // Used to delete user record
//    public void delete(int id){
//        try{
//            // Remover Usuario
//        }catch(Exception e){
//            System.out.println(e);
//        }
//    }
//}
