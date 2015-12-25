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
import com.shelby.carnet.entities.Comptes;
import com.shelby.carnet.entities.Configurations;
import com.shelby.carnet.entities.Contacts;
import com.shelby.carnet.entities.Historiques;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author shelby
 */
public class HistoriquesJpaController implements Serializable {

    public HistoriquesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Historiques historiques) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comptes idCpts = historiques.getIdCpts();
            if (idCpts != null) {
                idCpts = em.getReference(idCpts.getClass(), idCpts.getIdCpts());
                historiques.setIdCpts(idCpts);
            }
            Configurations idconfig = historiques.getIdconfig();
            if (idconfig != null) {
                idconfig = em.getReference(idconfig.getClass(), idconfig.getIdconfig());
                historiques.setIdconfig(idconfig);
            }
            Contacts idCts = historiques.getIdCts();
            if (idCts != null) {
                idCts = em.getReference(idCts.getClass(), idCts.getIdCts());
                historiques.setIdCts(idCts);
            }
            em.persist(historiques);
            if (idCpts != null) {
                idCpts.getHistoriquesList().add(historiques);
                idCpts = em.merge(idCpts);
            }
            if (idconfig != null) {
                idconfig.getHistoriquesList().add(historiques);
                idconfig = em.merge(idconfig);
            }
            if (idCts != null) {
                idCts.getHistoriquesList().add(historiques);
                idCts = em.merge(idCts);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Historiques historiques) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Historiques persistentHistoriques = em.find(Historiques.class, historiques.getIdHisto());
            Comptes idCptsOld = persistentHistoriques.getIdCpts();
            Comptes idCptsNew = historiques.getIdCpts();
            Configurations idconfigOld = persistentHistoriques.getIdconfig();
            Configurations idconfigNew = historiques.getIdconfig();
            Contacts idCtsOld = persistentHistoriques.getIdCts();
            Contacts idCtsNew = historiques.getIdCts();
            if (idCptsNew != null) {
                idCptsNew = em.getReference(idCptsNew.getClass(), idCptsNew.getIdCpts());
                historiques.setIdCpts(idCptsNew);
            }
            if (idconfigNew != null) {
                idconfigNew = em.getReference(idconfigNew.getClass(), idconfigNew.getIdconfig());
                historiques.setIdconfig(idconfigNew);
            }
            if (idCtsNew != null) {
                idCtsNew = em.getReference(idCtsNew.getClass(), idCtsNew.getIdCts());
                historiques.setIdCts(idCtsNew);
            }
            historiques = em.merge(historiques);
            if (idCptsOld != null && !idCptsOld.equals(idCptsNew)) {
                idCptsOld.getHistoriquesList().remove(historiques);
                idCptsOld = em.merge(idCptsOld);
            }
            if (idCptsNew != null && !idCptsNew.equals(idCptsOld)) {
                idCptsNew.getHistoriquesList().add(historiques);
                idCptsNew = em.merge(idCptsNew);
            }
            if (idconfigOld != null && !idconfigOld.equals(idconfigNew)) {
                idconfigOld.getHistoriquesList().remove(historiques);
                idconfigOld = em.merge(idconfigOld);
            }
            if (idconfigNew != null && !idconfigNew.equals(idconfigOld)) {
                idconfigNew.getHistoriquesList().add(historiques);
                idconfigNew = em.merge(idconfigNew);
            }
            if (idCtsOld != null && !idCtsOld.equals(idCtsNew)) {
                idCtsOld.getHistoriquesList().remove(historiques);
                idCtsOld = em.merge(idCtsOld);
            }
            if (idCtsNew != null && !idCtsNew.equals(idCtsOld)) {
                idCtsNew.getHistoriquesList().add(historiques);
                idCtsNew = em.merge(idCtsNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = historiques.getIdHisto();
                if (findHistoriques(id) == null) {
                    throw new NonexistentEntityException("The historiques with id " + id + " no longer exists.");
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
            Historiques historiques;
            try {
                historiques = em.getReference(Historiques.class, id);
                historiques.getIdHisto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The historiques with id " + id + " no longer exists.", enfe);
            }
            Comptes idCpts = historiques.getIdCpts();
            if (idCpts != null) {
                idCpts.getHistoriquesList().remove(historiques);
                idCpts = em.merge(idCpts);
            }
            Configurations idconfig = historiques.getIdconfig();
            if (idconfig != null) {
                idconfig.getHistoriquesList().remove(historiques);
                idconfig = em.merge(idconfig);
            }
            Contacts idCts = historiques.getIdCts();
            if (idCts != null) {
                idCts.getHistoriquesList().remove(historiques);
                idCts = em.merge(idCts);
            }
            em.remove(historiques);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Historiques> findHistoriquesEntities() {
        return findHistoriquesEntities(true, -1, -1);
    }

    public List<Historiques> findHistoriquesEntities(int maxResults, int firstResult) {
        return findHistoriquesEntities(false, maxResults, firstResult);
    }

    private List<Historiques> findHistoriquesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Historiques.class));
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

    public Historiques findHistoriques(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Historiques.class, id);
        } finally {
            em.close();
        }
    }

    public int getHistoriquesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Historiques> rt = cq.from(Historiques.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
