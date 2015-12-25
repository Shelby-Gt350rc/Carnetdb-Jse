/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shelby.carnet.controllers;

import com.shelby.carnet.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.shelby.carnet.entities.Contacts;
import com.shelby.carnet.entities.Filieres;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author shelby
 */
public class FilieresJpaController implements Serializable {

    public FilieresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Filieres filieres) {
        if (filieres.getContactsList() == null) {
            filieres.setContactsList(new ArrayList<Contacts>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Contacts> attachedContactsList = new ArrayList<Contacts>();
            for (Contacts contactsListContactsToAttach : filieres.getContactsList()) {
                contactsListContactsToAttach = em.getReference(contactsListContactsToAttach.getClass(), contactsListContactsToAttach.getIdCts());
                attachedContactsList.add(contactsListContactsToAttach);
            }
            filieres.setContactsList(attachedContactsList);
            em.persist(filieres);
            for (Contacts contactsListContacts : filieres.getContactsList()) {
                Filieres oldIdFilOfContactsListContacts = contactsListContacts.getIdFil();
                contactsListContacts.setIdFil(filieres);
                contactsListContacts = em.merge(contactsListContacts);
                if (oldIdFilOfContactsListContacts != null) {
                    oldIdFilOfContactsListContacts.getContactsList().remove(contactsListContacts);
                    oldIdFilOfContactsListContacts = em.merge(oldIdFilOfContactsListContacts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Filieres filieres) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Filieres persistentFilieres = em.find(Filieres.class, filieres.getIdFil());
            List<Contacts> contactsListOld = persistentFilieres.getContactsList();
            List<Contacts> contactsListNew = filieres.getContactsList();
            List<Contacts> attachedContactsListNew = new ArrayList<Contacts>();
            for (Contacts contactsListNewContactsToAttach : contactsListNew) {
                contactsListNewContactsToAttach = em.getReference(contactsListNewContactsToAttach.getClass(), contactsListNewContactsToAttach.getIdCts());
                attachedContactsListNew.add(contactsListNewContactsToAttach);
            }
            contactsListNew = attachedContactsListNew;
            filieres.setContactsList(contactsListNew);
            filieres = em.merge(filieres);
            for (Contacts contactsListOldContacts : contactsListOld) {
                if (!contactsListNew.contains(contactsListOldContacts)) {
                    contactsListOldContacts.setIdFil(null);
                    contactsListOldContacts = em.merge(contactsListOldContacts);
                }
            }
            for (Contacts contactsListNewContacts : contactsListNew) {
                if (!contactsListOld.contains(contactsListNewContacts)) {
                    Filieres oldIdFilOfContactsListNewContacts = contactsListNewContacts.getIdFil();
                    contactsListNewContacts.setIdFil(filieres);
                    contactsListNewContacts = em.merge(contactsListNewContacts);
                    if (oldIdFilOfContactsListNewContacts != null && !oldIdFilOfContactsListNewContacts.equals(filieres)) {
                        oldIdFilOfContactsListNewContacts.getContactsList().remove(contactsListNewContacts);
                        oldIdFilOfContactsListNewContacts = em.merge(oldIdFilOfContactsListNewContacts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = filieres.getIdFil();
                if (findFilieres(id) == null) {
                    throw new NonexistentEntityException("The filieres with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Filieres filieres;
            try {
                filieres = em.getReference(Filieres.class, id);
                filieres.getIdFil();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The filieres with id " + id + " no longer exists.", enfe);
            }
            List<Contacts> contactsList = filieres.getContactsList();
            for (Contacts contactsListContacts : contactsList) {
                contactsListContacts.setIdFil(null);
                contactsListContacts = em.merge(contactsListContacts);
            }
            em.remove(filieres);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Filieres> findFilieresEntities() {
        return findFilieresEntities(true, -1, -1);
    }

    public List<Filieres> findFilieresEntities(int maxResults, int firstResult) {
        return findFilieresEntities(false, maxResults, firstResult);
    }

    private List<Filieres> findFilieresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Filieres.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Filieres findFilieres(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Filieres.class, id);
        } finally {
            em.close();
        }
    }

    public int getFilieresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Filieres> rt = cq.from(Filieres.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
