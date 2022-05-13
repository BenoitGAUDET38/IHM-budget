<?php

//getting the dboperation class
require_once 'includes/DbOperation.php';

//function validating all the paramters are available
//we will pass the required parameters to this function
function isTheseParametersAvailable($params)
{
    //assuming all parameters are available
    $available = true;
    $missingparams = "";

    foreach ($params as $param) {
        if (!isset($_POST[$param]) || strlen($_POST[$param]) <= 0) {
            $available = false;
            $missingparams = $missingparams . ", " . $param;
        }
    }

    //if parameters are missing
    if (!$available) {
        $response = array();
        $response['error'] = true;
        $response['message'] = 'Parameters ' . substr($missingparams, 1, strlen($missingparams)) . ' missing';

        //displaying error
        echo json_encode($response);

        //stopping further execution
        die();
    }
}

//an array to display response
$response = array();

//if it is an api call
//that means a get parameter named api call is set in the URL
//and with this parameter we are concluding that it is an api call
if (isset($_GET['apicall'])) {

    switch ($_GET['apicall']) {

        //the CREATE operation
        //if the api call value is 'createhero'
        //we will create a record in the database
        case 'createtransaction':
            //first check the parameters required for this request are available or not
            isTheseParametersAvailable(array('nom', 'categorie', 'provenance', 'montant', 'devise', 'commentaire'));

            //creating a new dboperation object
            $db = new DbOperation();

            //creating a new record in the database
            $result = $db->createTransaction(
                $_POST['nom'],
                $_POST['categorie'],
                $_POST['provenance'],
                $_POST['montant'],
                $_POST['devise'],
                $_POST['commentaire']
            );


            //if the record is created adding success to response
            if ($result) {
                //record is created means there is no error
                $response['error'] = false;

                //in message we have a success message
                $response['message'] = 'La transaction a été créée';

                //and we are getting all the heroes from the database in the response
                $response['transactions'] = $db->getTransactions();
            } else {
                //if record is not added that means there is an error
                $response['error'] = true;

                //and we have the error message
                $response['message'] = 'Some error occurred please try again';
            }
            break;
        case 'createcategorie':
            //first check the parameters required for this request are available or not
            isTheseParametersAvailable(array('nom','seuil'));

            //creating a new dboperation object
            $db = new DbOperation();

            //creating a new record in the database
            $result = $db->createCategorie(
                $_POST['nom'],
                $_POST['seuil']
            );


            //if the record is created adding success to response
            if ($result) {
                //record is created means there is no error
                $response['error'] = false;

                //in message we have a success message
                $response['message'] = 'La categorie a été créée';

                //and we are getting all the heroes from the database in the response
                $response['categories'] = $db->getCategories();
            } else {
                //if record is not added that means there is an error
                $response['error'] = true;

                //and we have the error message
                $response['message'] = 'Some error occurred please try again';
            }
            break;

        //the READ operation
        //if the call is getheroes
        case 'gettransactions':
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'Liste recupérée avec succès';
            $response['transactions'] = $db->getTransactions();
            break;
        //the READ operation
        //if the call is getheroes
        case 'getcategories':
            $db = new DbOperation();
            $response['error'] = false;
            $response['message'] = 'Liste recupérée avec succès';
            $response['categories'] = $db->getCategories();
            break;
        //the UPDATE operation
        case 'updatetransaction':
            isTheseParametersAvailable(array('id', 'nom', 'montant', 'devise', 'categorie', 'commentaire', 'provenance'));
            $db = new DbOperation();
            $result = $db->updateTransaction(
                $_POST['id'],
                $_POST['nom'],
                $_POST['montant'],
                $_POST['devise'],
                $_POST['categorie'],
                $_POST['commentaire'],
                $_POST['provenance']
            );

            if ($result) {
                $response['error'] = false;
                $response['message'] = 'Transaction modifiée avec succès';
                $response['transactions'] = $db->getTransactions();
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occurred please try again';
            }
            break;
        //the UPDATE operation
        case 'updatecategorie':
            isTheseParametersAvailable(array('id', 'nom','seuil'));
            $db = new DbOperation();
            $result = $db->updateCategorie(
                $_POST['id'],
                $_POST['nom'],
                $_POST['seuil']
            );

            if ($result) {
                $response['error'] = false;
                $response['message'] = 'Categorie modifiée avec succès';
                $response['categories'] = $db->getCategories();
            } else {
                $response['error'] = true;
                $response['message'] = 'Some error occurred please try again';
            }
            break;

        //the delete operation
        case 'deletetransaction':

            //for the delete operation we are getting a GET parameter from the url having the id of the record to be deleted
            if (isset($_GET['id'])) {
                $db = new DbOperation();
                if ($db->deleteTransaction($_GET['id'])) {
                    $response['error'] = false;
                    $response['message'] = 'Transaction supprimée';
                    $response['transactions'] = $db->getTransactions();
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Some error occurred please try again';
                }
            } else {
                $response['error'] = true;
                $response['message'] = 'Veuillez fournir un objet';
            }
            break;
        case 'deletecategorie':

            //for the delete operation we are getting a GET parameter from the url having the id of the record to be deleted
            if (isset($_GET['id'])) {
                $db = new DbOperation();
                if ($db->deleteCategorie($_GET['id'])) {
                    $response['error'] = false;
                    $response['message'] = 'Categorie supprimée';
                    $response['transactions'] = $db->getCategories();
                } else {
                    $response['error'] = true;
                    $response['message'] = 'Some error occurred please try again';
                }
            } else {
                $response['error'] = true;
                $response['message'] = 'Veuillez fournir un objet';
            }
            break;
    }

} else {
    //if it is not api call
    //pushing appropriate values to response array
    $response['error'] = true;
    $response['message'] = 'Invalid API Call';
}

//displaying the response in json structure
echo json_encode($response);
