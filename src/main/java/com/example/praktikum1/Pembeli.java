/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.praktikum1;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER DJOGJA
 */
@Entity
@Table(name = "pembeli")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pembeli.findAll", query = "SELECT p FROM Pembeli p"),
    @NamedQuery(name = "Pembeli.findByIdpembeli", query = "SELECT p FROM Pembeli p WHERE p.idpembeli = :idpembeli"),
    @NamedQuery(name = "Pembeli.findByNama", query = "SELECT p FROM Pembeli p WHERE p.nama = :nama"),
    @NamedQuery(name = "Pembeli.findByJeniskelamin", query = "SELECT p FROM Pembeli p WHERE p.jeniskelamin = :jeniskelamin")})
public class Pembeli implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idpembeli")
    private Integer idpembeli;
    @Basic(optional = false)
    @Column(name = "nama")
    private String nama;
    @Basic(optional = false)
    @Column(name = "jeniskelamin")
    private String jeniskelamin;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "idpembeli")
    private Transaksi transaksi;

    public Pembeli() {
    }

    public Pembeli(Integer idpembeli) {
        this.idpembeli = idpembeli;
    }

    public Pembeli(Integer idpembeli, String nama, String jeniskelamin) {
        this.idpembeli = idpembeli;
        this.nama = nama;
        this.jeniskelamin = jeniskelamin;
    }

    public Integer getIdpembeli() {
        return idpembeli;
    }

    public void setIdpembeli(Integer idpembeli) {
        this.idpembeli = idpembeli;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJeniskelamin() {
        return jeniskelamin;
    }

    public void setJeniskelamin(String jeniskelamin) {
        this.jeniskelamin = jeniskelamin;
    }

    public Transaksi getTransaksi() {
        return transaksi;
    }

    public void setTransaksi(Transaksi transaksi) {
        this.transaksi = transaksi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpembeli != null ? idpembeli.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pembeli)) {
            return false;
        }
        Pembeli other = (Pembeli) object;
        if ((this.idpembeli == null && other.idpembeli != null) || (this.idpembeli != null && !this.idpembeli.equals(other.idpembeli))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.praktikum1.Pembeli[ idpembeli=" + idpembeli + " ]";
    }
    
}
