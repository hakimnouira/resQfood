<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Dcategory
 *
 * @ORM\Table(name="dcategory")
 * @ORM\Entity
 */
class Dcategory
{
    /**
     * @var int
     *
     * @ORM\Column(name="dcategory_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $dcategoryId;

    /**
     * @var string
     *
     * @ORM\Column(name="dcategory_name", type="string", length=255, nullable=false)
     */
    private $dcategoryName;

    /**
     * @var string|null
     *
     * @ORM\Column(name="dcategory_description", type="string", length=255, nullable=true)
     */
    private $dcategoryDescription;

    public function getDcategoryId(): ?int
    {
        return $this->dcategoryId;
    }

    public function getDcategoryName(): ?string
    {
        return $this->dcategoryName;
    }

    public function setDcategoryName(string $dcategoryName): static
    {
        $this->dcategoryName = $dcategoryName;

        return $this;
    }

    public function getDcategoryDescription(): ?string
    {
        return $this->dcategoryDescription;
    }

    public function setDcategoryDescription(?string $dcategoryDescription): static
    {
        $this->dcategoryDescription = $dcategoryDescription;

        return $this;
    }


}
