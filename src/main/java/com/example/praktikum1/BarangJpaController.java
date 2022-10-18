/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.praktikum1;

import com.example.praktikum1.exceptions.IllegalOrphanException;
import com.example.praktikum1.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author USER DJOGJA
 */
public class BarangJpaController implements Serializable {

    public BarangJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.example_praktikum1_jar_0.0.1-SNAPSHOTPU");

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public BarangJpaController() {
    }
    
    

    public void create(Barang barang) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Transaksi transaksi = barang.getTransaksi();
            if (transaksi != null) {
                transaksi = em.getReference(transaksi.getClass(), transaksi.getIdtransaksi());
                barang.setTransaksi(transaksi);
            }
            em.persist(barang);
            if (transaksi != null) {
                Barang oldIdbarangOfTransaksi = transaksi.getIdbarang();
                if (oldIdbarangOfTransaksi != null) {
                    oldIdbarangOfTransaksi.setTransaksi(null);
                    oldIdbarangOfTransaksi = em.merge(oldIdbarangOfTransaksi);
                }
                transaksi.setIdbarang(barang);
                transaksi = em.merge(transaksi);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Barang barang) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Barang persistentBarang = em.find(Barang.class, barang.getIdbarang());
            Transaksi transaksiOld = persistentBarang.getTransaksi();
            Transaksi transaksiNew = barang.getTransaksi();
            List<String> illegalOrphanMessages = null;
            if (transaksiOld != null && !transaksiOld.equals(transaksiNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Transaksi " + transaksiOld + " since its idbarang field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (transaksiNew != null) {
                transaksiNew = em.getReference(transaksiNew.getClass(), transaksiNew.getIdtransaksi());
                barang.setTransaksi(transaksiNew);
            }
            barang = em.merge(barang);
            if (transaksiNew != null && !transaksiNew.equals(transaksiOld)) {
                Barang oldIdbarangOfTransaksi = transaksiNew.getIdbarang();
                if (oldIdbarangOfTransaksi != null) {
                    oldIdbarangOfTransaksi.setTransaksi(null);
                    oldIdbarangOfTransaksi = em.merge(oldIdbarangOfTransaksi);
                }
                transaksiNew.setIdbarang(barang);
                transaksiNew = em.merge(transaksiNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = barang.getIdbarang();
                if (findBarang(id) == null) {
                    throw new NonexistentEntityException("The barang with id " + id + " no longer exists.");
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
            Barang barang;
            try {
                barang = em.getReference(Barang.class, id);
                barang.getIdbarang();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The barang with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Transaksi transaksiOrphanCheck = barang.getTransaksi();
            if (transaksiOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Barang (" + barang + ") cannot be destroyed since the Transaksi " + transaksiOrphanCheck + " in its transaksi field has a non-nullable idbarang field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(barang);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Barang> findBarangEntities() {
        return findBarangEntities(true, -1, -1);
    }

    public List<Barang> findBarangEntities(int maxResults, int firstResult) {
        return findBarangEntities(false, maxResults, firstResult);
    }

    private List<Barang> findBarangEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Barang.class));
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

    public Barang findBarang(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Barang.class, id);
        } finally {
            em.close();
        }
    }

    public int getBarangCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Barang> rt = cq.from(Barang.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
