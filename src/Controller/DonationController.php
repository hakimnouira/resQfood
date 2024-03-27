<?php

namespace App\Controller;

use App\Entity\Donation;
use App\Form\DonationType;
use Doctrine\ORM\EntityManagerInterface;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use App\Form\FoodType;
use App\Form\RawMaterialsType;
use Symfony\Component\Form\FormError;
use Symfony\Component\HttpFoundation\Session\SessionInterface;



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
    

    #[Route('/donation/money', name: 'app_money')]
public function donateMoney(Request $request, SessionInterface $session): Response
{
    $donation = new Donation();
    $donation->setDonationCategory('Money'); // Set the category here

    // Create the form with the DonationType
    $form = $this->createForm(DonationType::class, $donation);

    // Handle form submission
    $form->handleRequest($request);

    if ($form->isSubmitted() && $form->isValid()) {
        // Temporary workaround to set foodName to an empty string
        if ($donation->getDonationCategory() === 'Money') {
            $donation->setFoodName('');
            $donation->setFoodQuantity(0);
        }

        $entityManager = $this->getDoctrine()->getManager();
        $entityManager->persist($donation);
        $entityManager->flush();

        // Store success message in session
        $session->getFlashBag()->add('success', 'Donation added successfully!');

        // Redirect back to the money donation page
        return $this->redirectToRoute('app_money');
    }

    // Handle empty field validation manually
    if ($form->isSubmitted() && !$form->isValid() && $form->get('donationAmount')->getData() === null) {
        $form->get('donationAmount')->addError(new FormError('Please fill in this field.'));
    }

    // Render the template with the donationAdded variable
    return $this->render('donation/money.html.twig', [
        'form' => $form->createView(),
        'donationAdded' => $session->getFlashBag()->get('success'), // Pass the success message to the template
    ]);
}
    
    
    #[Route('/donation/food', name: 'app_food')]
    public function donateFood(Request $request): Response
    {
        $donation = new Donation();
        $donation->setDonationCategory('Food'); // Set the category here
    
        // Create the form with the FoodType
        $form = $this->createForm(FoodType::class, $donation);
    
        // Handle form submission
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            // Temporary workaround to set foodName to an empty string
            if ($donation->getDonationCategory() === 'Food') {
            
                $donation->setDonationAmount(0);
            } 
             $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($donation);
            $entityManager->flush();
    
    
    
            // Redirect back to the food donation page
            return $this->redirectToRoute('app_food');
        }
    
        return $this->render('donation/food.html.twig', [
            'form' => $form->createView(),
        ]);
    }
    
    #[Route('/donation/rawmaterials', name: 'app_raw_materials')]
    public function donateRawMaterials(Request $request): Response
    {
        $donation = new Donation();
        $donation->setDonationCategory('Raw Materials'); // Set the category here
    
        // Create the form with the appropriate form type for raw materials
        $form = $this->createForm(RawMaterialsType::class, $donation); // Update RawMaterialsType with the correct form type
    
        // Handle form submission
        $form->handleRequest($request);
    
        if ($form->isSubmitted() && $form->isValid()) {
            // Temporary workaround to set foodName to an empty string
            if ($donation->getDonationCategory() === 'Raw Materials') {
            
                $donation->setDonationAmount(0);
            } 
             $entityManager = $this->getDoctrine()->getManager();
            $entityManager->persist($donation);
            $entityManager->flush();
    
            // Redirect back to the raw materials donation page
            return $this->redirectToRoute('app_raw_materials');
        }
    
        return $this->render('donation/raw_materials.html.twig', [
            'form' => $form->createView(),
        ]);
    }
    

    

}