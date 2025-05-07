package fr.tp.eni.encheresskyjo.dal;

import fr.tp.eni.encheresskyjo.bo.Pickup;

public interface PickupDAO {
    void create(int articleId, Pickup pickup);
    Pickup read(int articleId);
    void update(int articleId, Pickup pickup);
    void delete(int articleId);
}
