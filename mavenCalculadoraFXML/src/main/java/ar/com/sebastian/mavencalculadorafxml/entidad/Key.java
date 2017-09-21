/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ar.com.sebastian.mavencalculadorafxml.entidad;

/**
 *
 * @author sscardig
 */
public abstract class Key {
    
    private String key;
    private boolean active;

    public Key(String key, boolean active) {
        this.key = key;
        this.active = active;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
