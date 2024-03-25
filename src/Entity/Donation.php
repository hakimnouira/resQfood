<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Donation
 *
 * @ORM\Table(name="donation")
 * @ORM\Entity
 */
class Donation
{
    /**
     * @var int
     *
     * @ORM\Column(name="donation_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $donationId;

    /**
     * @var string
     *
     * @ORM\Column(name="donation_category", type="string", length=255, nullable=false)
     */
    private $donationCategory;

    /**
     * @var float|null
     *
     * @ORM\Column(name="donation_amount", type="float", precision=10, scale=0, nullable=true)
     */
    private $donationAmount;

    /**
     * @var string|null
     *
     * @ORM\Column(name="food_name", type="string", length=255, nullable=true)
     */
    private $foodName;

    /**
     * @var float
     *
     * @ORM\Column(name="food_quantity", type="float", precision=10, scale=0, nullable=false)
     */
    private $foodQuantity;

    /**
     * @var int|null
     *
     * @ORM\Column(name="dcategory_id", type="integer", nullable=true)
     */
    private $dcategoryId;

    /**
     * @var int
     *
     * @ORM\Column(name="udonor_id", type="integer", nullable=false)
     */
    private $udonorId;

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


}
