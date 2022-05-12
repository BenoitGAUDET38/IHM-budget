<?php

class DbOperation
{
    //Database connection link
    private $con;

    //Class constructor
    function __construct()
    {
        //Getting the DbConnect.php file
        require_once dirname(__FILE__) . '/DbConnect.php';

        //Creating a DbConnect object to connect to the database
        $db = new DbConnect();

        //Initializing our connection link of this class
        //by calling the method connect of DbConnect class
        $this->con = $db->connect();
    }

    /*
    * The create operation
    * When this method is called a new record is created in the database
    */

    function createTransaction($nom, $categorie, $provenance, $montant, $devise, $commentaire)
    {
        $stmt = $this->con->prepare("INSERT INTO transaction (nom, categorie, provenance,montant, devise,commentaire) VALUES (?,?,?,?,?,?)");
        $stmt->bind_param("sisdss", $nom, $categorie, $provenance, $montant, $devise, $commentaire);
        if ($stmt->execute())
            return true;
        return false;
    }

    public function createCategorie($nom,$seuil)
    {
        $stmt = $this->con->prepare("INSERT INTO categorie (nom,seuil) VALUES (?,?)");
        $stmt->bind_param("sd", $nom, $seuil);
        if ($stmt->execute())
            return true;
        return false;
    }

    /*
    * The read operation
    * When this method is called it is returning all the existing record of the database
    */
    function getTransactions()
    {
        $stmt = $this->con->prepare("SELECT id,nom, montant, devise, categorie, commentaire, provenance, date FROM transaction");
        $stmt->execute();
        $stmt->bind_result($id, $nom, $montant, $devise, $categorie, $commentaire, $provenance, $date);

        $transactions = array();

        while ($stmt->fetch()) {
            $transaction = array();
            $transaction['id'] = $id;
            $transaction['nom'] = $nom;
            $transaction['montant'] = $montant;
            $transaction['devise'] = $devise;
            $transaction['categorie'] = $categorie;
            $transaction['commentaire'] = $commentaire;
            $transaction['provenance'] = $provenance;
            $transaction['date'] = $date;
            array_push($transactions, $transaction);
        }
        return $transactions;
    }

    public function getCategories()
    {
        $stmt = $this->con->prepare("SELECT id,nom,seuil FROM categorie");
        $stmt->execute();
        $stmt->bind_result($id, $nom,$seuil);
        $categories = array();
        while ($stmt->fetch()) {
            $categorie = array();
            $categorie['id'] = $id;
            $categorie['nom'] = $nom;
            $categorie['seuil'] = $seuil;
            array_push($categories, $categorie);
        }
        return $categories;
    }

    /*
    * The update operation
    * When this method is called the record with the given id is updated with the new given values
    */
    function updateTransaction($id, $nom, $montant, $devise, $categorie, $commentaire, $provenance)
    {
        $stmt = $this->con->prepare("UPDATE transaction SET nom = ?, montant = ?, devise = ?, categorie = ? , commentaire = ?, provenance = ? WHERE id = ?");
        $stmt->bind_param("sdsissi", $nom, $montant, $devise, $categorie, $commentaire, $provenance, $id);
        if ($stmt->execute())
            return true;
        return false;
    }

    public function updateCategorie($id, $nom, $seuil)
    {
        $stmt = $this->con->prepare("UPDATE categorie SET nom = ?, seuil= ? WHERE id = ?");
        $stmt->bind_param("sdi", $nom, $seuil, $id);
        if ($stmt->execute())
            return true;
        return false;
    }

    /*
    * The delete operation
    * When this method is called record is deleted for the given id
    */
    function deleteTransaction($id)
    {
        $stmt = $this->con->prepare("DELETE FROM transaction WHERE id = ? ");
        $stmt->bind_param("i", $id);
        if ($stmt->execute())
            return true;
        return false;
    }

    public function deleteCategorie($id)
    {
        $stmt = $this->con->prepare("DELETE FROM categorie WHERE id = ? ");
        $stmt->bind_param("i", $id);
        if ($stmt->execute())
            return true;
        return false;
    }


}
 