<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Participations
 *
 * @ORM\Table(name="participations", indexes={@ORM\Index(name="fk_ide", columns={"id_event"})})
 * @ORM\Entity
 */
class Participations
{
    /**
     * @var int
     *
     * @ORM\Column(name="idP", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idp;

    /**
     * @var int
     *
     * @ORM\Column(name="id_event", type="integer", nullable=false)
     */
    private $idEvent;

    /**
     * @var string
     *
     * @ORM\Column(name="particip_name", type="string", length=200, nullable=false)
     */
    private $participName;

    public function getIdp(): ?int
    {
        return $this->idp;
    }

    public function getIdEvent(): ?int
    {
        return $this->idEvent;
    }

    public function setIdEvent(int $idEvent): static
    {
        $this->idEvent = $idEvent;

        return $this;
    }

    public function getParticipName(): ?string
    {
        return $this->participName;
    }

    public function setParticipName(string $participName): static
    {
        $this->participName = $participName;

        return $this;
    }


}
