/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shelby.carnet.controllers;

import com.shelby.carnet.controllers.exceptions.NonexistentEntityException;
import com.shelby.carnet.entities.Comptes;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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
public class ComptesJpaController implements Serializable {

    public ComptesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comptes comptes) {
        if (comptes.getHistoriquesList() == null) {
            comptes.setHistoriquesList(new ArrayList<Historiques>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Groupes idGrps = comptes.getIdGrps();
            if (idGrps != null) {
                idGrps = em.getReference(idGrps.getClass(), idGrps.getIdGrps());
                comptes.setIdGrps(idGrps);
            }
            List<Historiques> attachedHistoriquesList = new ArrayList<Historiques>();
            for (Historiques historiquesListHistoriquesToAttach : comptes.getHistoriquesList()) {
                historiquesListHistoriquesToAttach = em.getReference(historiquesListHistoriquesToAttach.getClass(), historiquesListHistoriquesToAttach.getIdHisto());
                attachedHistoriquesList.add(historiquesListHistoriquesToAttach);
            }
            comptes.setHistoriquesList(attachedHistoriquesList);
            em.persist(comptes);
            if (idGrps != null) {
                idGrps.getComptesList().add(comptes);
                idGrps = em.merge(idGrps);
            }
            for (Historiques historiquesListHistoriques : comptes.getHistoriquesList()) {
                Comptes oldIdCptsOfHistoriquesListHistoriques = historiquesListHistoriques.getIdCpts();
                historiquesListHistoriques.setIdCpts(comptes);
                historiquesListHistoriques = em.merge(historiquesListHistoriques);
                if (oldIdCptsOfHistoriquesListHistoriques != null) {
                    oldIdCptsOfHistoriquesListHistoriques.getHistoriquesList().remove(historiquesListHistoriques);
                    oldIdCptsOfHistoriquesListHistoriques = em.merge(oldIdCptsOfHistoriquesListHistoriques);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comptes comptes) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comptes persistentComptes = em.find(Comptes.class, comptes.getIdCpts());
            Groupes idGrpsOld = persistentComptes.getIdGrps();
            Groupes idGrpsNew = comptes.getIdGrps();
            List<Historiques> historiquesListOld = persistentComptes.getHistoriquesList();
            List<Historiques> historiquesListNew = comptes.getHistoriquesList();
            if (idGrpsNew != null) {
                idGrpsNew = em.getReference(idGrpsNew.getClass(), idGrpsNew.getIdGrps());
                comptes.setIdGrps(idGrpsNew);
            }
            List<Historiques> attachedHistoriquesListNew = new ArrayList<Historiques>();
            for (Historiques historiquesListNewHistoriquesToAttach : historiquesListNew) {
                historiquesListNewHistoriquesToAttach = em.getReference(historiquesListNewHistoriquesToAttach.getClass(), historiquesListNewHistoriquesToAttach.getIdHisto());
                attachedHistoriquesListNew.add(historiquesListNewHistoriquesToAttach);
            }
            historiquesListNew = attachedHistoriquesListNew;
            comptes.setHistoriquesList(historiquesListNew);
            comptes = em.merge(comptes);
            if (idGrpsOld != null && !idGrpsOld.equals(idGrpsNew)) {
                idGrpsOld.getComptesList().remove(comptes);
                idGrpsOld = em.merge(idGrpsOld);
            }
            if (idGrpsNew != null && !idGrpsNew.equals(idGrpsOld)) {
                idGrpsNew.getComptesList().add(comptes);
                idGrpsNew = em.merge(idGrpsNew);
            }
            for (Historiques historiquesListOldHistoriques : historiquesListOld) {
                if (!historiquesListNew.contains(historiquesListOldHistoriques)) {
                    historiquesListOldHistoriques.setIdCpts(null);
                    historiquesListOldHistoriques = em.merge(historiquesListOldHistoriques);
                }
            }
            for (Historiques historiquesListNewHistoriques : historiquesListNew) {
                if (!historiquesListOld.contains(historiquesListNewHistoriques)) {
                    Comptes oldIdCptsOfHistoriquesListNewHistoriques = historiquesListNewHistoriques.getIdCpts();
                    historiquesListNewHistoriques.setIdCpts(comptes);
                    historiquesListNewHistoriques = em.merge(historiquesListNewHistoriques);
                    if (oldIdCptsOfHistoriquesListNewHistoriques != null && !oldIdCptsOfHistoriquesListNewHistoriques.equals(comptes)) {
                        oldIdCptsOfHistoriquesListNewHistoriques.getHistoriquesList().remove(historiquesListNewHistoriques);
                        oldIdCptsOfHistoriquesListNewHistoriques = em.merge(oldIdCptsOfHistoriquesListNewHistoriques);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comptes.getIdCpts();
                if (findComptes(id) == null) {
                    throw new NonexistentEntityException("The comptes with id " + id + " no longer exists.");
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
            Comptes comptes;
            try {
                comptes = em.getReference(Comptes.class, id);
                comptes.getIdCpts();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comptes with id " + id + " no longer exists.", enfe);
            }
            Groupes idGrps = comptes.getIdGrps();
            if (idGrps != null) {
                idGrps.getComptesList().remove(comptes);
                idGrps = em.merge(idGrps);
            }
            List<Historiques> historiquesList = comptes.getHistoriquesList();
            for (Historiques historiquesListHistoriques : historiquesList) {
                historiquesListHistoriques.setIdCpts(null);
                historiquesListHistoriques = em.merge(historiquesListHistoriques);
            }
            em.remove(comptes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comptes> findComptesEntities() {
        return findComptesEntities(true, -1, -1);
    }

    public List<Comptes> findComptesEntities(int maxResults, int firstResult) {
        return findComptesEntities(false, maxResults, firstResult);
    }

    private List<Comptes> findComptesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comptes.class));
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

    public Comptes findComptes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comptes.class, id);
        } finally {
            em.close();
        }
    }

    public int getComptesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comptes> rt = cq.from(Comptes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
