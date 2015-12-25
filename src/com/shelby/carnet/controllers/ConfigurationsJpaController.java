/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shelby.carnet.controllers;

import com.shelby.carnet.controllers.exceptions.NonexistentEntityException;
import com.shelby.carnet.entities.Configurations;
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
public class ConfigurationsJpaController implements Serializable {

    public ConfigurationsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Configurations configurations) {
        if (configurations.getHistoriquesList() == null) {
            configurations.setHistoriquesList(new ArrayList<Historiques>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Groupes idGrps = configurations.getIdGrps();
            if (idGrps != null) {
                idGrps = em.getReference(idGrps.getClass(), idGrps.getIdGrps());
                configurations.setIdGrps(idGrps);
            }
            List<Historiques> attachedHistoriquesList = new ArrayList<Historiques>();
            for (Historiques historiquesListHistoriquesToAttach : configurations.getHistoriquesList()) {
                historiquesListHistoriquesToAttach = em.getReference(historiquesListHistoriquesToAttach.getClass(), historiquesListHistoriquesToAttach.getIdHisto());
                attachedHistoriquesList.add(historiquesListHistoriquesToAttach);
            }
            configurations.setHistoriquesList(attachedHistoriquesList);
            em.persist(configurations);
            if (idGrps != null) {
                idGrps.getConfigurationsList().add(configurations);
                idGrps = em.merge(idGrps);
            }
            for (Historiques historiquesListHistoriques : configurations.getHistoriquesList()) {
                Configurations oldIdconfigOfHistoriquesListHistoriques = historiquesListHistoriques.getIdconfig();
                historiquesListHistoriques.setIdconfig(configurations);
                historiquesListHistoriques = em.merge(historiquesListHistoriques);
                if (oldIdconfigOfHistoriquesListHistoriques != null) {
                    oldIdconfigOfHistoriquesListHistoriques.getHistoriquesList().remove(historiquesListHistoriques);
                    oldIdconfigOfHistoriquesListHistoriques = em.merge(oldIdconfigOfHistoriquesListHistoriques);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Configurations configurations) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Configurations persistentConfigurations = em.find(Configurations.class, configurations.getIdconfig());
            Groupes idGrpsOld = persistentConfigurations.getIdGrps();
            Groupes idGrpsNew = configurations.getIdGrps();
            List<Historiques> historiquesListOld = persistentConfigurations.getHistoriquesList();
            List<Historiques> historiquesListNew = configurations.getHistoriquesList();
            if (idGrpsNew != null) {
                idGrpsNew = em.getReference(idGrpsNew.getClass(), idGrpsNew.getIdGrps());
                configurations.setIdGrps(idGrpsNew);
            }
            List<Historiques> attachedHistoriquesListNew = new ArrayList<Historiques>();
            for (Historiques historiquesListNewHistoriquesToAttach : historiquesListNew) {
                historiquesListNewHistoriquesToAttach = em.getReference(historiquesListNewHistoriquesToAttach.getClass(), historiquesListNewHistoriquesToAttach.getIdHisto());
                attachedHistoriquesListNew.add(historiquesListNewHistoriquesToAttach);
            }
            historiquesListNew = attachedHistoriquesListNew;
            configurations.setHistoriquesList(historiquesListNew);
            configurations = em.merge(configurations);
            if (idGrpsOld != null && !idGrpsOld.equals(idGrpsNew)) {
                idGrpsOld.getConfigurationsList().remove(configurations);
                idGrpsOld = em.merge(idGrpsOld);
            }
            if (idGrpsNew != null && !idGrpsNew.equals(idGrpsOld)) {
                idGrpsNew.getConfigurationsList().add(configurations);
                idGrpsNew = em.merge(idGrpsNew);
            }
            for (Historiques historiquesListOldHistoriques : historiquesListOld) {
                if (!historiquesListNew.contains(historiquesListOldHistoriques)) {
                    historiquesListOldHistoriques.setIdconfig(null);
                    historiquesListOldHistoriques = em.merge(historiquesListOldHistoriques);
                }
            }
            for (Historiques historiquesListNewHistoriques : historiquesListNew) {
                if (!historiquesListOld.contains(historiquesListNewHistoriques)) {
                    Configurations oldIdconfigOfHistoriquesListNewHistoriques = historiquesListNewHistoriques.getIdconfig();
                    historiquesListNewHistoriques.setIdconfig(configurations);
                    historiquesListNewHistoriques = em.merge(historiquesListNewHistoriques);
                    if (oldIdconfigOfHistoriquesListNewHistoriques != null && !oldIdconfigOfHistoriquesListNewHistoriques.equals(configurations)) {
                        oldIdconfigOfHistoriquesListNewHistoriques.getHistoriquesList().remove(historiquesListNewHistoriques);
                        oldIdconfigOfHistoriquesListNewHistoriques = em.merge(oldIdconfigOfHistoriquesListNewHistoriques);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = configurations.getIdconfig();
                if (findConfigurations(id) == null) {
                    throw new NonexistentEntityException("The configurations with id " + id + " no longer exists.");
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
            Configurations configurations;
            try {
                configurations = em.getReference(Configurations.class, id);
                configurations.getIdconfig();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The configurations with id " + id + " no longer exists.", enfe);
            }
            Groupes idGrps = configurations.getIdGrps();
            if (idGrps != null) {
                idGrps.getConfigurationsList().remove(configurations);
                idGrps = em.merge(idGrps);
            }
            List<Historiques> historiquesList = configurations.getHistoriquesList();
            for (Historiques historiquesListHistoriques : historiquesList) {
                historiquesListHistoriques.setIdconfig(null);
                historiquesListHistoriques = em.merge(historiquesListHistoriques);
            }
            em.remove(configurations);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Configurations> findConfigurationsEntities() {
        return findConfigurationsEntities(true, -1, -1);
    }

    public List<Configurations> findConfigurationsEntities(int maxResults, int firstResult) {
        return findConfigurationsEntities(false, maxResults, firstResult);
    }

    private List<Configurations> findConfigurationsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Configurations.class));
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

    public Configurations findConfigurations(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Configurations.class, id);
        } finally {
            em.close();
        }
    }

    public int getConfigurationsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Configurations> rt = cq.from(Configurations.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
