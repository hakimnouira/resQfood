<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Notifications
 *
 * @ORM\Table(name="notifications")
 * @ORM\Entity
 */
class Notifications
{
    /**
     * @var int
     *
     * @ORM\Column(name="id_notif", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $idNotif;

    /**
     * @var string|null
     *
     * @ORM\Column(name="message_notif", type="string", length=255, nullable=true)
     */
    private $messageNotif;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="timestamp", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $timestamp = 'CURRENT_TIMESTAMP';

    public function getIdNotif(): ?int
    {
        return $this->idNotif;
    }

    public function getMessageNotif(): ?string
    {
        return $this->messageNotif;
    }

    public function setMessageNotif(?string $messageNotif): static
    {
        $this->messageNotif = $messageNotif;

        return $this;
    }

    public function getTimestamp(): ?\DateTimeInterface
    {
        return $this->timestamp;
    }

    public function setTimestamp(\DateTimeInterface $timestamp): static
    {
        $this->timestamp = $timestamp;

        return $this;
    }


}
