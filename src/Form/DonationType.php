<?php

namespace App\Form;

use App\Entity\Donation;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\NumberType;
use Symfony\Component\Form\FormEvent;
use Symfony\Component\Form\FormEvents; // Add this line
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\TextType;

class DonationType extends AbstractType
{
   /* public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('donationCategory')
            ->add('donationAmount')
            ->add('foodName')
            ->add('foodQuantity')
            ->add('dcategoryId')
            ->add('udonorId')
        ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Donation::class,
        ]);
    }*/

    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('donationAmount', NumberType::class, [
                'label' => 'Donation Amount',
                // Add any other options for this field
            ]);
          
    }

            

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Donation::class,
            'donationCategory' => null, // Define the 'donationCategory' option and set it to null by default
        ]);
    }


    

    

}

