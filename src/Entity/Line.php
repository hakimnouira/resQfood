<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Line
 *
 * @ORM\Table(name="line", indexes={@ORM\Index(name="fk1", columns={"basket_id"}), @ORM\Index(name="fk2", columns={"user_id"}), @ORM\Index(name="product_id", columns={"product_id"})})
 * @ORM\Entity
 */
class Line
{
    /**
     * @var int
     *
     * @ORM\Column(name="line_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $lineId;

    /**
     * @var int
     *
     * @ORM\Column(name="line_quantity", type="integer", nullable=false)
     */
    private $lineQuantity;

    /**
     * @var int
     *
     * @ORM\Column(name="product_id", type="integer", nullable=false)
     */
    private $productId;

    /**
     * @var int
     *
     * @ORM\Column(name="basket_id", type="integer", nullable=false)
     */
    private $basketId;

    /**
     * @var int
     *
     * @ORM\Column(name="user_id", type="integer", nullable=false)
     */
    private $userId;

    /**
     * @var \DateTime|null
     *
     * @ORM\Column(name="line_date", type="date", nullable=true, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $lineDate = 'CURRENT_TIMESTAMP';

    public function getLineId(): ?int
    {
        return $this->lineId;
    }

    public function getLineQuantity(): ?int
    {
        return $this->lineQuantity;
    }

    public function setLineQuantity(int $lineQuantity): static
    {
        $this->lineQuantity = $lineQuantity;

        return $this;
    }

    public function getProductId(): ?int
    {
        return $this->productId;
    }

    public function setProductId(int $productId): static
    {
        $this->productId = $productId;

        return $this;
    }

    public function getBasketId(): ?int
    {
        return $this->basketId;
    }

    public function setBasketId(int $basketId): static
    {
        $this->basketId = $basketId;

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

    public function getLineDate(): ?\DateTimeInterface
    {
        return $this->lineDate;
    }

    public function setLineDate(?\DateTimeInterface $lineDate): static
    {
        $this->lineDate = $lineDate;

        return $this;
    }


}
