/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shelby.carnet.controllers;

import com.shelby.carnet.controllers.exceptions.IllegalOrphanException;
import com.shelby.carnet.controllers.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.shelby.carnet.entities.Comptes;
import java.util.ArrayList;
import java.util.List;
import com.shelby.carnet.entities.Configurations;
import com.shelby.carnet.entities.Contacts;
import com.shelby.carnet.entities.Groupes;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author shelby
 */
public class GroupesJpaController implements Serializable {

    public GroupesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Groupes groupes) {
        if (groupes.getComptesList() == null) {
            groupes.setComptesList(new ArrayList<Comptes>());
        }
        if (groupes.getConfigurationsList() == null) {
            groupes.setConfigurationsList(new ArrayList<Configurations>());
        }
        if (groupes.getContactsList() == null) {
            groupes.setContactsList(new ArrayList<Contacts>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Comptes> attachedComptesList = new ArrayList<Comptes>();
            for (Comptes comptesListComptesToAttach : groupes.getComptesList()) {
                comptesListComptesToAttach = em.getReference(comptesListComptesToAttach.getClass(), comptesListComptesToAttach.getIdCpts());
                attachedComptesList.add(comptesListComptesToAttach);
            }
            groupes.setComptesList(attachedComptesList);
            List<Configurations> attachedConfigurationsList = new ArrayList<Configurations>();
            for (Configurations configurationsListConfigurationsToAttach : groupes.getConfigurationsList()) {
                configurationsListConfigurationsToAttach = em.getReference(configurationsListConfigurationsToAttach.getClass(), configurationsListConfigurationsToAttach.getIdconfig());
                attachedConfigurationsList.add(configurationsListConfigurationsToAttach);
            }
            groupes.setConfigurationsList(attachedConfigurationsList);
            List<Contacts> attachedContactsList = new ArrayList<Contacts>();
            for (Contacts contactsListContactsToAttach : groupes.getContactsList()) {
                contactsListContactsToAttach = em.getReference(contactsListContactsToAttach.getClass(), contactsListContactsToAttach.getIdCts());
                attachedContactsList.add(contactsListContactsToAttach);
            }
            groupes.setContactsList(attachedContactsList);
            em.persist(groupes);
            for (Comptes comptesListComptes : groupes.getComptesList()) {
                Groupes oldIdGrpsOfComptesListComptes = comptesListComptes.getIdGrps();
                comptesListComptes.setIdGrps(groupes);
                comptesListComptes = em.merge(comptesListComptes);
                if (oldIdGrpsOfComptesListComptes != null) {
                    oldIdGrpsOfComptesListComptes.getComptesList().remove(comptesListComptes);
                    oldIdGrpsOfComptesListComptes = em.merge(oldIdGrpsOfComptesListComptes);
                }
            }
            for (Configurations configurationsListConfigurations : groupes.getConfigurationsList()) {
                Groupes oldIdGrpsOfConfigurationsListConfigurations = configurationsListConfigurations.getIdGrps();
                configurationsListConfigurations.setIdGrps(groupes);
                configurationsListConfigurations = em.merge(configurationsListConfigurations);
                if (oldIdGrpsOfConfigurationsListConfigurations != null) {
                    oldIdGrpsOfConfigurationsListConfigurations.getConfigurationsList().remove(configurationsListConfigurations);
                    oldIdGrpsOfConfigurationsListConfigurations = em.merge(oldIdGrpsOfConfigurationsListConfigurations);
                }
            }
            for (Contacts contactsListContacts : groupes.getContactsList()) {
                Groupes oldIdGrpsOfContactsListContacts = contactsListContacts.getIdGrps();
                contactsListContacts.setIdGrps(groupes);
                contactsListContacts = em.merge(contactsListContacts);
                if (oldIdGrpsOfContactsListContacts != null) {
                    oldIdGrpsOfContactsListContacts.getContactsList().remove(contactsListContacts);
                    oldIdGrpsOfContactsListContacts = em.merge(oldIdGrpsOfContactsListContacts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Groupes groupes) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Groupes persistentGroupes = em.find(Groupes.class, groupes.getIdGrps());
            List<Comptes> comptesListOld = persistentGroupes.getComptesList();
            List<Comptes> comptesListNew = groupes.getComptesList();
            List<Configurations> configurationsListOld = persistentGroupes.getConfigurationsList();
            List<Configurations> configurationsListNew = groupes.getConfigurationsList();
            List<Contacts> contactsListOld = persistentGroupes.getContactsList();
            List<Contacts> contactsListNew = groupes.getContactsList();
            List<String> illegalOrphanMessages = null;
            for (Comptes comptesListOldComptes : comptesListOld) {
                if (!comptesListNew.contains(comptesListOldComptes)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Comptes " + comptesListOldComptes + " since its idGrps field is not nullable.");
                }
            }
            for (Configurations configurationsListOldConfigurations : configurationsListOld) {
                if (!configurationsListNew.contains(configurationsListOldConfigurations)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Configurations " + configurationsListOldConfigurations + " since its idGrps field is not nullable.");
                }
            }
            for (Contacts contactsListOldContacts : contactsListOld) {
                if (!contactsListNew.contains(contactsListOldContacts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Contacts " + contactsListOldContacts + " since its idGrps field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Comptes> attachedComptesListNew = new ArrayList<Comptes>();
            for (Comptes comptesListNewComptesToAttach : comptesListNew) {
                comptesListNewComptesToAttach = em.getReference(comptesListNewComptesToAttach.getClass(), comptesListNewComptesToAttach.getIdCpts());
                attachedComptesListNew.add(comptesListNewComptesToAttach);
            }
            comptesListNew = attachedComptesListNew;
            groupes.setComptesList(comptesListNew);
            List<Configurations> attachedConfigurationsListNew = new ArrayList<Configurations>();
            for (Configurations configurationsListNewConfigurationsToAttach : configurationsListNew) {
                configurationsListNewConfigurationsToAttach = em.getReference(configurationsListNewConfigurationsToAttach.getClass(), configurationsListNewConfigurationsToAttach.getIdconfig());
                attachedConfigurationsListNew.add(configurationsListNewConfigurationsToAttach);
            }
            configurationsListNew = attachedConfigurationsListNew;
            groupes.setConfigurationsList(configurationsListNew);
            List<Contacts> attachedContactsListNew = new ArrayList<Contacts>();
            for (Contacts contactsListNewContactsToAttach : contactsListNew) {
                contactsListNewContactsToAttach = em.getReference(contactsListNewContactsToAttach.getClass(), contactsListNewContactsToAttach.getIdCts());
                attachedContactsListNew.add(contactsListNewContactsToAttach);
            }
            contactsListNew = attachedContactsListNew;
            groupes.setContactsList(contactsListNew);
            groupes = em.merge(groupes);
            for (Comptes comptesListNewComptes : comptesListNew) {
                if (!comptesListOld.contains(comptesListNewComptes)) {
                    Groupes oldIdGrpsOfComptesListNewComptes = comptesListNewComptes.getIdGrps();
                    comptesListNewComptes.setIdGrps(groupes);
                    comptesListNewComptes = em.merge(comptesListNewComptes);
                    if (oldIdGrpsOfComptesListNewComptes != null && !oldIdGrpsOfComptesListNewComptes.equals(groupes)) {
                        oldIdGrpsOfComptesListNewComptes.getComptesList().remove(comptesListNewComptes);
                        oldIdGrpsOfComptesListNewComptes = em.merge(oldIdGrpsOfComptesListNewComptes);
                    }
                }
            }
            for (Configurations configurationsListNewConfigurations : configurationsListNew) {
                if (!configurationsListOld.contains(configurationsListNewConfigurations)) {
                    Groupes oldIdGrpsOfConfigurationsListNewConfigurations = configurationsListNewConfigurations.getIdGrps();
                    configurationsListNewConfigurations.setIdGrps(groupes);
                    configurationsListNewConfigurations = em.merge(configurationsListNewConfigurations);
                    if (oldIdGrpsOfConfigurationsListNewConfigurations != null && !oldIdGrpsOfConfigurationsListNewConfigurations.equals(groupes)) {
                        oldIdGrpsOfConfigurationsListNewConfigurations.getConfigurationsList().remove(configurationsListNewConfigurations);
                        oldIdGrpsOfConfigurationsListNewConfigurations = em.merge(oldIdGrpsOfConfigurationsListNewConfigurations);
                    }
                }
            }
            for (Contacts contactsListNewContacts : contactsListNew) {
                if (!contactsListOld.contains(contactsListNewContacts)) {
                    Groupes oldIdGrpsOfContactsListNewContacts = contactsListNewContacts.getIdGrps();
                    contactsListNewContacts.setIdGrps(groupes);
                    contactsListNewContacts = em.merge(contactsListNewContacts);
                    if (oldIdGrpsOfContactsListNewContacts != null && !oldIdGrpsOfContactsListNewContacts.equals(groupes)) {
                        oldIdGrpsOfContactsListNewContacts.getContactsList().remove(contactsListNewContacts);
                        oldIdGrpsOfContactsListNewContacts = em.merge(oldIdGrpsOfContactsListNewContacts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = groupes.getIdGrps();
                if (findGroupes(id) == null) {
                    throw new NonexistentEntityException("The groupes with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Groupes groupes;
            try {
                groupes = em.getReference(Groupes.class, id);
                groupes.getIdGrps();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The groupes with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Comptes> comptesListOrphanCheck = groupes.getComptesList();
            for (Comptes comptesListOrphanCheckComptes : comptesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Groupes (" + groupes + ") cannot be destroyed since the Comptes " + comptesListOrphanCheckComptes + " in its comptesList field has a non-nullable idGrps field.");
            }
            List<Configurations> configurationsListOrphanCheck = groupes.getConfigurationsList();
            for (Configurations configurationsListOrphanCheckConfigurations : configurationsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Groupes (" + groupes + ") cannot be destroyed since the Configurations " + configurationsListOrphanCheckConfigurations + " in its configurationsList field has a non-nullable idGrps field.");
            }
            List<Contacts> contactsListOrphanCheck = groupes.getContactsList();
            for (Contacts contactsListOrphanCheckContacts : contactsListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Groupes (" + groupes + ") cannot be destroyed since the Contacts " + contactsListOrphanCheckContacts + " in its contactsList field has a non-nullable idGrps field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(groupes);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Groupes> findGroupesEntities() {
        return findGroupesEntities(true, -1, -1);
    }

    public List<Groupes> findGroupesEntities(int maxResults, int firstResult) {
        return findGroupesEntities(false, maxResults, firstResult);
    }

    private List<Groupes> findGroupesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Groupes.class));
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

    public Groupes findGroupes(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Groupes.class, id);
        } finally {
            em.close();
        }
    }

    public int getGroupesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Groupes> rt = cq.from(Groupes.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
