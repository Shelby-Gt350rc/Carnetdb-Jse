/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shelby.carnet.controllers;

import com.shelby.carnet.controllers.exceptions.NonexistentEntityException;
import com.shelby.carnet.entities.Contacts;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.shelby.carnet.entities.Filieres;
import com.shelby.carnet.entities.Groupes;
import com.shelby.carnet.entities.Historiques;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author shelby
 */
public class ContactsJpaController implements Serializable {

    public ContactsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Contacts contacts) {
        if (contacts.getHistoriquesList() == null) {
            contacts.setHistoriquesList(new ArrayList<Historiques>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Filieres idFil = contacts.getIdFil();
            if (idFil != null) {
                idFil = em.getReference(idFil.getClass(), idFil.getIdFil());
                contacts.setIdFil(idFil);
            }
            Groupes idGrps = contacts.getIdGrps();
            if (idGrps != null) {
                idGrps = em.getReference(idGrps.getClass(), idGrps.getIdGrps());
                contacts.setIdGrps(idGrps);
            }
            List<Historiques> attachedHistoriquesList = new ArrayList<Historiques>();
            for (Historiques historiquesListHistoriquesToAttach : contacts.getHistoriquesList()) {
                historiquesListHistoriquesToAttach = em.getReference(historiquesListHistoriquesToAttach.getClass(), historiquesListHistoriquesToAttach.getIdHisto());
                attachedHistoriquesList.add(historiquesListHistoriquesToAttach);
            }
            contacts.setHistoriquesList(attachedHistoriquesList);
            em.persist(contacts);
            if (idFil != null) {
                idFil.getContactsList().add(contacts);
                idFil = em.merge(idFil);
            }
            if (idGrps != null) {
                idGrps.getContactsList().add(contacts);
                idGrps = em.merge(idGrps);
            }
            for (Historiques historiquesListHistoriques : contacts.getHistoriquesList()) {
                Contacts oldIdCtsOfHistoriquesListHistoriques = historiquesListHistoriques.getIdCts();
                historiquesListHistoriques.setIdCts(contacts);
                historiquesListHistoriques = em.merge(historiquesListHistoriques);
                if (oldIdCtsOfHistoriquesListHistoriques != null) {
                    oldIdCtsOfHistoriquesListHistoriques.getHistoriquesList().remove(historiquesListHistoriques);
                    oldIdCtsOfHistoriquesListHistoriques = em.merge(oldIdCtsOfHistoriquesListHistoriques);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Contacts contacts) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Contacts persistentContacts = em.find(Contacts.class, contacts.getIdCts());
            Filieres idFilOld = persistentContacts.getIdFil();
            Filieres idFilNew = contacts.getIdFil();
            Groupes idGrpsOld = persistentContacts.getIdGrps();
            Groupes idGrpsNew = contacts.getIdGrps();
            List<Historiques> historiquesListOld = persistentContacts.getHistoriquesList();
            List<Historiques> historiquesListNew = contacts.getHistoriquesList();
            if (idFilNew != null) {
                idFilNew = em.getReference(idFilNew.getClass(), idFilNew.getIdFil());
                contacts.setIdFil(idFilNew);
            }
            if (idGrpsNew != null) {
                idGrpsNew = em.getReference(idGrpsNew.getClass(), idGrpsNew.getIdGrps());
                contacts.setIdGrps(idGrpsNew);
            }
            List<Historiques> attachedHistoriquesListNew = new ArrayList<Historiques>();
            for (Historiques historiquesListNewHistoriquesToAttach : historiquesListNew) {
                historiquesListNewHistoriquesToAttach = em.getReference(historiquesListNewHistoriquesToAttach.getClass(), historiquesListNewHistoriquesToAttach.getIdHisto());
                attachedHistoriquesListNew.add(historiquesListNewHistoriquesToAttach);
            }
            historiquesListNew = attachedHistoriquesListNew;
            contacts.setHistoriquesList(historiquesListNew);
            contacts = em.merge(contacts);
            if (idFilOld != null && !idFilOld.equals(idFilNew)) {
                idFilOld.getContactsList().remove(contacts);
                idFilOld = em.merge(idFilOld);
            }
            if (idFilNew != null && !idFilNew.equals(idFilOld)) {
                idFilNew.getContactsList().add(contacts);
                idFilNew = em.merge(idFilNew);
            }
            if (idGrpsOld != null && !idGrpsOld.equals(idGrpsNew)) {
                idGrpsOld.getContactsList().remove(contacts);
                idGrpsOld = em.merge(idGrpsOld);
            }
            if (idGrpsNew != null && !idGrpsNew.equals(idGrpsOld)) {
                idGrpsNew.getContactsList().add(contacts);
                idGrpsNew = em.merge(idGrpsNew);
            }
            for (Historiques historiquesListOldHistoriques : historiquesListOld) {
                if (!historiquesListNew.contains(historiquesListOldHistoriques)) {
                    historiquesListOldHistoriques.setIdCts(null);
                    historiquesListOldHistoriques = em.merge(historiquesListOldHistoriques);
                }
            }
            for (Historiques historiquesListNewHistoriques : historiquesListNew) {
                if (!historiquesListOld.contains(historiquesListNewHistoriques)) {
                    Contacts oldIdCtsOfHistoriquesListNewHistoriques = historiquesListNewHistoriques.getIdCts();
                    historiquesListNewHistoriques.setIdCts(contacts);
                    historiquesListNewHistoriques = em.merge(historiquesListNewHistoriques);
                    if (oldIdCtsOfHistoriquesListNewHistoriques != null && !oldIdCtsOfHistoriquesListNewHistoriques.equals(contacts)) {
                        oldIdCtsOfHistoriquesListNewHistoriques.getHistoriquesList().remove(historiquesListNewHistoriques);
                        oldIdCtsOfHistoriquesListNewHistoriques = em.merge(oldIdCtsOfHistoriquesListNewHistoriques);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = contacts.getIdCts();
                if (findContacts(id) == null) {
                    throw new NonexistentEntityException("The contacts with id " + id + " no longer exists.");
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
            Contacts contacts;
            try {
                contacts = em.getReference(Contacts.class, id);
                contacts.getIdCts();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contacts with id " + id + " no longer exists.", enfe);
            }
            Filieres idFil = contacts.getIdFil();
            if (idFil != null) {
                idFil.getContactsList().remove(contacts);
                idFil = em.merge(idFil);
            }
            Groupes idGrps = contacts.getIdGrps();
            if (idGrps != null) {
                idGrps.getContactsList().remove(contacts);
                idGrps = em.merge(idGrps);
            }
            List<Historiques> historiquesList = contacts.getHistoriquesList();
            for (Historiques historiquesListHistoriques : historiquesList) {
                historiquesListHistoriques.setIdCts(null);
                historiquesListHistoriques = em.merge(historiquesListHistoriques);
            }
            em.remove(contacts);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Contacts> findContactsEntities() {
        return findContactsEntities(true, -1, -1);
    }

    public List<Contacts> findContactsEntities(int maxResults, int firstResult) {
        return findContactsEntities(false, maxResults, firstResult);
    }

    private List<Contacts> findContactsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Contacts.class));
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

    public Contacts findContacts(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Contacts.class, id);
        } finally {
            em.close();
        }
    }

    public int getContactsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Contacts> rt = cq.from(Contacts.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
