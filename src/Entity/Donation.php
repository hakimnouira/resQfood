<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;
use App\Repository\DonationRepository;
use App\Entity\Dcategory;

#[ORM\Entity(repositoryClass: DonationRepository::class)]
class Donation
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $donationId = null;

    #[ORM\Column(length: 150)]
    private ?string $donationCategory = null;

    #[ORM\Column]
    private ?float $donationAmount = null;

    #[ORM\Column(length: 150)]
    private ?string $foodName = null;

    #[ORM\Column]
    private ?float $foodQuantity = null;

    //#[ORM\ManyToOne(inversedBy:'Donations')]
    //private ?Dcategory $dcategory = null;

   // #[ORM\Column]
   // private ?int $dcategoryId = null;

    #[ORM\Column]
    private ?int $udonorId = null;

    

    public function getDonationId(): ?int
    {
        return $this->donationId;
    }

    public function getDonationCategory(): ?string
    {
        return $this->donationCategory;
    }

    public function setDonationCategory(string $donationCategory): static
    {
        $this->donationCategory = $donationCategory;

        return $this;
    }

    public function getDonationAmount(): ?float
    {
        return $this->donationAmount;
    }

    public function setDonationAmount(?float $donationAmount): static
    {
        $this->donationAmount = $donationAmount;

        return $this;
    }

    public function getFoodName(): ?string
    {
        return $this->foodName;
    }

    public function setFoodName(?string $foodName): static
    {
        $this->foodName = $foodName;

        return $this;
    }

    public function getFoodQuantity(): ?float
    {
        return $this->foodQuantity;
    }

    public function setFoodQuantity(float $foodQuantity): static
    {
        $this->foodQuantity = $foodQuantity;

        return $this;
    }

    public function getDcategoryId(): ?int
    {
        return $this->dcategoryId;
    }

    public function setDcategoryId(?int $dcategoryId): static
    {
        $this->dcategoryId = $dcategoryId;

        return $this;
    }

    public function getUdonorId(): ?int
    {
        return $this->udonorId;
    }

    public function setUdonorId(int $udonorId): static
    {
        $this->udonorId = $udonorId;
        return $this;
    }

    public function __construct(string $donationCategory = null)
{
    // Initialize properties based on the donation category
    // Only initialize if the category is known
    if ($donationCategory === 'Money') {
        $this->foodName = ''; // Set foodName to an empty string for money donations
        $this->foodQuantity = 0; // Also set foodQuantity to 0
    } elseif ($donationCategory === 'Food') {
        // For food category, set only the common properties to 0
        $this->donationAmount = 0;
    }

    // Common properties
    $this->udonorId = 0;
    $this->dcategoryId = 0;
}


   
}