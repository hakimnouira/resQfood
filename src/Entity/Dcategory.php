<?php

namespace App\Entity;

use App\Repository\DcategoryRepository;
use Doctrine\ORM\Mapping as ORM;

#[ORM\Entity(repositoryClass: DcategoryRepository::class)]
class Dcategory
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column]
    private ?int $dcategoryId = null;

    #[ORM\Column(length: 255)]
    private ?string $dcategoryName = null;
   
    #[ORM\Column(length: 255)]
    private ?string $dcategoryDescription = null;
   
    

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
