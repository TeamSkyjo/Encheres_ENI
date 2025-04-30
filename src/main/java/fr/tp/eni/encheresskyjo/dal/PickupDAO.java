package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.Pickup;

public interface PickupDAO {
    void create(Pickup pickup);
    Pickup read(int articleId);
    void update(Pickup pickup);
}
