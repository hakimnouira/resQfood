<?php

namespace App\Entity;

use Doctrine\DBAL\Types\Types;
use Doctrine\ORM\Mapping as ORM;

/**
 * Likes
 *
 * @ORM\Table(name="likes", indexes={@ORM\Index(name="post_id", columns={"post_id"})})
 * @ORM\Entity
 */
class Likes
{
    /**
     * @var int
     *
     * @ORM\Column(name="like_id", type="integer", nullable=false)
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="IDENTITY")
     */
    private $likeId;

    /**
     * @var int|null
     *
     * @ORM\Column(name="post_id", type="integer", nullable=true)
     */
    private $postId;

    /**
     * @var int|null
     *
     * @ORM\Column(name="user_id", type="integer", nullable=true)
     */
    private $userId;

    /**
     * @var string|null
     *
     * @ORM\Column(name="reaction_type", type="string", length=0, nullable=true, options={"default"="NON"})
     */
    private $reactionType = 'NON';

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="created_at", type="datetime", nullable=false, options={"default"="CURRENT_TIMESTAMP"})
     */
    private $createdAt = 'CURRENT_TIMESTAMP';

    public function getLikeId(): ?int
    {
        return $this->likeId;
    }

    public function getPostId(): ?int
    {
        return $this->postId;
    }

    public function setPostId(?int $postId): static
    {
        $this->postId = $postId;

        return $this;
    }

    public function getUserId(): ?int
    {
        return $this->userId;
    }

    public function setUserId(?int $userId): static
    {
        $this->userId = $userId;

        return $this;
    }

    public function getReactionType(): ?string
    {
        return $this->reactionType;
    }

    public function setReactionType(?string $reactionType): static
    {
        $this->reactionType = $reactionType;

        return $this;
    }

    public function getCreatedAt(): ?\DateTimeInterface
    {
        return $this->createdAt;
    }

    public function setCreatedAt(\DateTimeInterface $createdAt): static
    {
        $this->createdAt = $createdAt;

        return $this;
    }


}
