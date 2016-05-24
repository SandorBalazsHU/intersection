/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.project.intersection.model.graphModel;

import java.util.ArrayList;
import hu.elte.project.intersection.model.Player;
import hu.elte.project.intersection.view.GamePanel;
import hu.elte.project.intersection.model.graphmodel.CKey;

/**
 *
 * @author sandorbalazs
 */
public class VPlayer {
    public ArrayList<Integer> x = new ArrayList<Integer>();
    public ArrayList<Integer> y = new ArrayList<Integer>();
    public Player player;
    public double alpha = 10;
    public double r = 5.0d;
    public int thickness = 5;
    public CKey controllKey;
    public double roll = 10.0d;
    public boolean isRemoteUser;
    public int padding = 200;
    
    public VPlayer(Player player){
        this.player = player;
        this.isRemoteUser = player.isRemoteUser();
        this.controllKey = player.getControllKey();
        

        int randomX = padding + (int)(Math.random() * (GamePanel.windowX-padding)-padding); 
        int randomY = padding + (int)(Math.random() * (GamePanel.windowY-padding)-padding);
        x.add(randomX);
        randomX += r;
        x.add(randomX);
        
        y.add(randomY);
        randomY +=r;
        y.add(randomY);
        
        int randomStartAlpha = 0 + (int)(Math.random() * 360);
        alpha = randomStartAlpha;
    }
}
