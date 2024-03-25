<?php

namespace App\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Testing
 *
 * @ORM\Table(name="testing")
 * @ORM\Entity
 */
class Testing
{
    /**
     * @var int
     *
     * @ORM\Column(name="udonor_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $udonorId;

    public function getUdonorId(): ?int
    {
        return $this->udonorId;
    }


}
