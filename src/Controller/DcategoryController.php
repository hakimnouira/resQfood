<?php

namespace App\Controller;

use App\Entity\Dcategory;
use App\Form\DcategoryType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/dcategory')]
class DcategoryController extends AbstractController
{
    #[Route('/', name: 'app_dcategory_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $dcategories = $entityManager
            ->getRepository(Dcategory::class)
            ->findAll();

        return $this->render('dcategory/index.html.twig', [
            'dcategories' => $dcategories,
        ]);
    }

    #[Route('/new', name: 'app_dcategory_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $dcategory = new Dcategory();
        $form = $this->createForm(DcategoryType::class, $dcategory);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($dcategory);
            $entityManager->flush();

            return $this->redirectToRoute('app_dcategory_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('dcategory/new.html.twig', [
            'dcategory' => $dcategory,
            'form' => $form,
        ]);
    }

    #[Route('/{dcategoryId}', name: 'app_dcategory_show', methods: ['GET'])]
    public function show(Dcategory $dcategory): Response
    {
        return $this->render('dcategory/show.html.twig', [
            'dcategory' => $dcategory,
        ]);
    }

    #[Route('/{dcategoryId}/edit', name: 'app_dcategory_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Dcategory $dcategory, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(DcategoryType::class, $dcategory);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_dcategory_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('dcategory/edit.html.twig', [
            'dcategory' => $dcategory,
            'form' => $form,
        ]);
    }

    #[Route('/{dcategoryId}', name: 'app_dcategory_delete', methods: ['POST'])]
    public function delete(Request $request, Dcategory $dcategory, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$dcategory->getDcategoryId(), $request->request->get('_token'))) {
            $entityManager->remove($dcategory);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_dcategory_index', [], Response::HTTP_SEE_OTHER);
    }
}
