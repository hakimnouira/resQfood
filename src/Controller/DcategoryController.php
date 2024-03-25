<?php

namespace App\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

class DcategoryController extends AbstractController
{
    #[Route('/dcategory', name: 'app_dcategory')]
    public function index(): Response
    {
        return $this->render('dcategory/index.html.twig', [
            'controller_name' => 'DcategoryController',
        ]);
    }
}
