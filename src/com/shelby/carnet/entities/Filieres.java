/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shelby.carnet.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shelby
 */
@Entity
@Table(name = "filieres")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Filieres.findAll", query = "SELECT f FROM Filieres f"),
    @NamedQuery(name = "Filieres.findByIdFil", query = "SELECT f FROM Filieres f WHERE f.idFil = :idFil"),
    @NamedQuery(name = "Filieres.findByCodeFil", query = "SELECT f FROM Filieres f WHERE f.codeFil = :codeFil"),
    @NamedQuery(name = "Filieres.findByIntituleFil", query = "SELECT f FROM Filieres f WHERE f.intituleFil = :intituleFil"),
    @NamedQuery(name = "Filieres.findByActiveFil", query = "SELECT f FROM Filieres f WHERE f.activeFil = :activeFil")})
public class Filieres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idFil")
    private Integer idFil;
    @Column(name = "codeFil")
    private String codeFil;
    @Column(name = "intituleFil")
    private String intituleFil;
    @Column(name = "activeFil")
    private Boolean activeFil;
    @OneToMany(mappedBy = "idFil")
    private List<Contacts> contactsList;

    public Filieres() {
    }

    public Filieres(Integer idFil) {
        this.idFil = idFil;
    }

    public Integer getIdFil() {
        return idFil;
    }

    public void setIdFil(Integer idFil) {
        this.idFil = idFil;
    }

    public String getCodeFil() {
        return codeFil;
    }

    public void setCodeFil(String codeFil) {
        this.codeFil = codeFil;
    }

    public String getIntituleFil() {
        return intituleFil;
    }

    public void setIntituleFil(String intituleFil) {
        this.intituleFil = intituleFil;
    }

    public Boolean getActiveFil() {
        return activeFil;
    }

    public void setActiveFil(Boolean activeFil) {
        this.activeFil = activeFil;
    }

    @XmlTransient
    public List<Contacts> getContactsList() {
        return contactsList;
    }

    public void setContactsList(List<Contacts> contactsList) {
        this.contactsList = contactsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFil != null ? idFil.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Filieres)) {
            return false;
        }
        Filieres other = (Filieres) object;
        if ((this.idFil == null && other.idFil != null) || (this.idFil != null && !this.idFil.equals(other.idFil))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shelby.carnet.entities.Filieres[ idFil=" + idFil + " ]";
    }
    
}
