<?php

namespace App\Controller;

use App\Entity\Donation;
use App\Form\DonationType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;

#[Route('/donation')]
class DonationController extends AbstractController
{

    #[Route('/donationn', name: 'app_donation')]
    public function index1(): Response
    {
        return $this->render('donation/donation.html.twig', [
            'controller_name' => 'DonationController',
        ]);
    }
    #[Route('/', name: 'app_donation_index', methods: ['GET'])]
    public function index(EntityManagerInterface $entityManager): Response
    {
        $donations = $entityManager
            ->getRepository(Donation::class)
            ->findAll();

        return $this->render('donation/index.html.twig', [
            'donations' => $donations,
        ]);
    }

    #[Route('/new', name: 'app_donation_new', methods: ['GET', 'POST'])]
    public function new(Request $request, EntityManagerInterface $entityManager): Response
    {
        $donation = new Donation();
        $form = $this->createForm(DonationType::class, $donation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->persist($donation);
            $entityManager->flush();

            return $this->redirectToRoute('app_donation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('donation/new.html.twig', [
            'donation' => $donation,
            'form' => $form,
        ]);
    }

    #[Route('/{donationId}', name: 'app_donation_show', methods: ['GET'])]
    public function show(Donation $donation): Response
    {
        return $this->render('donation/show.html.twig', [
            'donation' => $donation,
        ]);
    }

    #[Route('/{donationId}/edit', name: 'app_donation_edit', methods: ['GET', 'POST'])]
    public function edit(Request $request, Donation $donation, EntityManagerInterface $entityManager): Response
    {
        $form = $this->createForm(DonationType::class, $donation);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            $entityManager->flush();

            return $this->redirectToRoute('app_donation_index', [], Response::HTTP_SEE_OTHER);
        }

        return $this->renderForm('donation/edit.html.twig', [
            'donation' => $donation,
            'form' => $form,
        ]);
    }

    #[Route('/{donationId}', name: 'app_donation_delete', methods: ['POST'])]
    public function delete(Request $request, Donation $donation, EntityManagerInterface $entityManager): Response
    {
        if ($this->isCsrfTokenValid('delete'.$donation->getDonationId(), $request->request->get('_token'))) {
            $entityManager->remove($donation);
            $entityManager->flush();
        }

        return $this->redirectToRoute('app_donation_index', [], Response::HTTP_SEE_OTHER);
    }
   
}
