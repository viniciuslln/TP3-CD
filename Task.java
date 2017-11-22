/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tp3;

import java.io.Serializable;

/**
 *
 * @author 912313 dentro/fora
 */
public class Task implements Serializable{
    public int id;
    public double x;
    public double y;
    public boolean dentro;

    public Task(int id) {
        this.id = id;
    }
    
}
