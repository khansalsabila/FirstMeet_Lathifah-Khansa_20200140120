/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.praktikum1;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER DJOGJA
 */
@Entity
@Table(name = "transaksi")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transaksi.findAll", query = "SELECT t FROM Transaksi t"),
    @NamedQuery(name = "Transaksi.findByIdtransaksi", query = "SELECT t FROM Transaksi t WHERE t.idtransaksi = :idtransaksi"),
    @NamedQuery(name = "Transaksi.findByTanggal", query = "SELECT t FROM Transaksi t WHERE t.tanggal = :tanggal"),
    @NamedQuery(name = "Transaksi.findByQty", query = "SELECT t FROM Transaksi t WHERE t.qty = :qty"),
    @NamedQuery(name = "Transaksi.findByJumlahHarga", query = "SELECT t FROM Transaksi t WHERE t.jumlahHarga = :jumlahHarga"),
    @NamedQuery(name = "Transaksi.findByKembalian", query = "SELECT t FROM Transaksi t WHERE t.kembalian = :kembalian")})
public class Transaksi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtransaksi")
    private Integer idtransaksi;
    @Basic(optional = false)
    @Column(name = "tanggal")
    @Temporal(TemporalType.DATE)
    private Date tanggal;
    @Basic(optional = false)
    @Column(name = "qty")
    private String qty;
    @Basic(optional = false)
    @Column(name = "jumlah harga")
    private String jumlahHarga;
    @Basic(optional = false)
    @Column(name = "kembalian")
    private String kembalian;
    @JoinColumn(name = "idbarang", referencedColumnName = "idbarang")
    @OneToOne(optional = false)
    private Barang idbarang;
    @JoinColumn(name = "idpembeli", referencedColumnName = "idpembeli")
    @OneToOne(optional = false)
    private Pembeli idpembeli;
    @JoinColumn(name = "idpegawai", referencedColumnName = "idpegawai")
    @OneToOne(optional = false)
    private Pegawai idpegawai;

    public Transaksi() {
    }

    public Transaksi(Integer idtransaksi) {
        this.idtransaksi = idtransaksi;
    }

    public Transaksi(Integer idtransaksi, Date tanggal, String qty, String jumlahHarga, String kembalian) {
        this.idtransaksi = idtransaksi;
        this.tanggal = tanggal;
        this.qty = qty;
        this.jumlahHarga = jumlahHarga;
        this.kembalian = kembalian;
    }

    public Integer getIdtransaksi() {
        return idtransaksi;
    }

    public void setIdtransaksi(Integer idtransaksi) {
        this.idtransaksi = idtransaksi;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getJumlahHarga() {
        return jumlahHarga;
    }

    public void setJumlahHarga(String jumlahHarga) {
        this.jumlahHarga = jumlahHarga;
    }

    public String getKembalian() {
        return kembalian;
    }

    public void setKembalian(String kembalian) {
        this.kembalian = kembalian;
    }

    public Barang getIdbarang() {
        return idbarang;
    }

    public void setIdbarang(Barang idbarang) {
        this.idbarang = idbarang;
    }

    public Pembeli getIdpembeli() {
        return idpembeli;
    }

    public void setIdpembeli(Pembeli idpembeli) {
        this.idpembeli = idpembeli;
    }

    public Pegawai getIdpegawai() {
        return idpegawai;
    }

    public void setIdpegawai(Pegawai idpegawai) {
        this.idpegawai = idpegawai;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransaksi != null ? idtransaksi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transaksi)) {
            return false;
        }
        Transaksi other = (Transaksi) object;
        if ((this.idtransaksi == null && other.idtransaksi != null) || (this.idtransaksi != null && !this.idtransaksi.equals(other.idtransaksi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.example.praktikum1.Transaksi[ idtransaksi=" + idtransaksi + " ]";
    }
    
}
