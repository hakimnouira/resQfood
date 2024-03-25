<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Basket
 *
 * @ORM\Table(name="basket")
 * @ORM\Entity
 */
class Basket
{
    /**
     * @var int
     *
     * @ORM\Column(name="basket_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $basketId;

    /**
     * @var string|null
     *
     * @ORM\Column(name="basket_status", type="string", length=100, nullable=true)
     */
    private $basketStatus;

    /**
     * @var int
     *
     * @ORM\Column(name="user_id", type="integer", nullable=false)
     */
    private $userId;

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="confirmation_date", type="date", nullable=true, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $confirmationDate = 'CURRENT_TIMESTAMP';

    public function getBasketId(): ?int
    {
        return $this->basketId;
    }

    public function getBasketStatus(): ?string
    {
        return $this->basketStatus;
    }

    public function setBasketStatus(?string $basketStatus): static
    {
        $this->basketStatus = $basketStatus;

        return $this;
    }

    public function getUserId(): ?int
    {
        return $this->userId;
    }

    public function setUserId(int $userId): static
    {
        $this->userId = $userId;

        return $this;
    }

    public function getConfirmationDate(): ?\DateTimeInterface
    {
        return $this->confirmationDate;
    }

    public function setConfirmationDate(?\DateTimeInterface $confirmationDate): static
    {
        $this->confirmationDate = $confirmationDate;

        return $this;
    }


}
