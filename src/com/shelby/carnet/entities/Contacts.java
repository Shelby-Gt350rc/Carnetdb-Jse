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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
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
@Table(name = "contacts")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contacts.findAll", query = "SELECT c FROM Contacts c"),
    @NamedQuery(name = "Contacts.findByIdCts", query = "SELECT c FROM Contacts c WHERE c.idCts = :idCts"),
    @NamedQuery(name = "Contacts.findByNomCts", query = "SELECT c FROM Contacts c WHERE c.nomCts = :nomCts"),
    @NamedQuery(name = "Contacts.findByPrenomCts", query = "SELECT c FROM Contacts c WHERE c.prenomCts = :prenomCts"),
    @NamedQuery(name = "Contacts.findByTelephoneCts", query = "SELECT c FROM Contacts c WHERE c.telephoneCts = :telephoneCts"),
    @NamedQuery(name = "Contacts.findByJourDaniversaireCts", query = "SELECT c FROM Contacts c WHERE c.jourDaniversaireCts = :jourDaniversaireCts"),
    @NamedQuery(name = "Contacts.findByActiveCts", query = "SELECT c FROM Contacts c WHERE c.activeCts = :activeCts")})
public class Contacts implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idCts")
    private Integer idCts;
    @Basic(optional = false)
    @Column(name = "nomCts")
    private String nomCts;
    @Basic(optional = false)
    @Column(name = "prenomCts")
    private String prenomCts;
    @Basic(optional = false)
    @Column(name = "telephoneCts")
    private String telephoneCts;
    @Basic(optional = false)
    @Column(name = "jourDaniversaireCts")
    private String jourDaniversaireCts;
    @Basic(optional = false)
    @Lob
    @Column(name = "urlPhotosCts")
    private String urlPhotosCts;
    @Basic(optional = false)
    @Column(name = "activeCts")
    private boolean activeCts;
    @OneToMany(mappedBy = "idCts")
    private List<Historiques> historiquesList;
    @JoinColumn(name = "idFil", referencedColumnName = "idFil")
    @ManyToOne
    private Filieres idFil;
    @JoinColumn(name = "idGrps", referencedColumnName = "idGrps")
    @ManyToOne(optional = false)
    private Groupes idGrps;

    public Contacts() {
    }

    public Contacts(Integer idCts) {
        this.idCts = idCts;
    }

    public Contacts(Integer idCts, String nomCts, String prenomCts, String telephoneCts, String jourDaniversaireCts, String urlPhotosCts, boolean activeCts) {
        this.idCts = idCts;
        this.nomCts = nomCts;
        this.prenomCts = prenomCts;
        this.telephoneCts = telephoneCts;
        this.jourDaniversaireCts = jourDaniversaireCts;
        this.urlPhotosCts = urlPhotosCts;
        this.activeCts = activeCts;
    }

    public Integer getIdCts() {
        return idCts;
    }

    public void setIdCts(Integer idCts) {
        this.idCts = idCts;
    }

    public String getNomCts() {
        return nomCts;
    }

    public void setNomCts(String nomCts) {
        this.nomCts = nomCts;
    }

    public String getPrenomCts() {
        return prenomCts;
    }

    public void setPrenomCts(String prenomCts) {
        this.prenomCts = prenomCts;
    }

    public String getTelephoneCts() {
        return telephoneCts;
    }

    public void setTelephoneCts(String telephoneCts) {
        this.telephoneCts = telephoneCts;
    }

    public String getJourDaniversaireCts() {
        return jourDaniversaireCts;
    }

    public void setJourDaniversaireCts(String jourDaniversaireCts) {
        this.jourDaniversaireCts = jourDaniversaireCts;
    }

    public String getUrlPhotosCts() {
        return urlPhotosCts;
    }

    public void setUrlPhotosCts(String urlPhotosCts) {
        this.urlPhotosCts = urlPhotosCts;
    }

    public boolean getActiveCts() {
        return activeCts;
    }

    public void setActiveCts(boolean activeCts) {
        this.activeCts = activeCts;
    }

    @XmlTransient
    public List<Historiques> getHistoriquesList() {
        return historiquesList;
    }

    public void setHistoriquesList(List<Historiques> historiquesList) {
        this.historiquesList = historiquesList;
    }

    public Filieres getIdFil() {
        return idFil;
    }

    public void setIdFil(Filieres idFil) {
        this.idFil = idFil;
    }

    public Groupes getIdGrps() {
        return idGrps;
    }

    public void setIdGrps(Groupes idGrps) {
        this.idGrps = idGrps;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCts != null ? idCts.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contacts)) {
            return false;
        }
        Contacts other = (Contacts) object;
        if ((this.idCts == null && other.idCts != null) || (this.idCts != null && !this.idCts.equals(other.idCts))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.shelby.carnet.entities.Contacts[ idCts=" + idCts + " ]";
    }
    
}
