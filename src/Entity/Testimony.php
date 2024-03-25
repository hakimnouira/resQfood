<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Testimony
 *
 * @ORM\Table(name="testimony")
 * @ORM\Entity
 */
class Testimony
{
    /**
     * @var int
     *
     * @ORM\Column(name="t_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $tId;

    /**
     * @var int
     *
     * @ORM\Column(name="userId", type="integer", nullable=false)
     */
    private $userid;

    /**
     * @var string
     *
     * @ORM\Column(name="title", type="string", length=255, nullable=false)
     */
    private $title;

    /**
     * @var string
     *
     * @ORM\Column(name="txt", type="string", length=1000, nullable=false)
     */
    private $txt;

    /**
     * @var string|null
     *
     * @ORM\Column(name="status", type="string", length=50, nullable=true)
     */
    private $status;

    public function getTId(): ?int
    {
        return $this->tId;
    }

    public function getUserid(): ?int
    {
        return $this->userid;
    }

    public function setUserid(int $userid): static
    {
        $this->userid = $userid;

        return $this;
    }

    public function getTitle(): ?string
    {
        return $this->title;
    }

    public function setTitle(string $title): static
    {
        $this->title = $title;

        return $this;
    }

    public function getTxt(): ?string
    {
        return $this->txt;
    }

    public function setTxt(string $txt): static
    {
        $this->txt = $txt;

        return $this;
    }

    public function getStatus(): ?string
    {
        return $this->status;
    }

    public function setStatus(?string $status): static
    {
        $this->status = $status;

        return $this;
    }


}
