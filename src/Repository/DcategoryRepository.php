<?php

namespace App\Repository;

use App\Entity\Dcategory;
use Doctrine\Bundle\DoctrineBundle\Repository\ServiceEntityRepository;
use Doctrine\Persistence\ManagerRegistry;

/**
 * @extends ServiceEntityRepository<Dcategory>
 *
 * @method Dcategory|null find($id, $lockMode = null, $lockVersion = null)
 * @method Dcategory|null findOneBy(array $criteria, array $orderBy = null)
 * @method Dcategory[]    findAll()
 * @method Dcategory[]    findBy(array $criteria, array $orderBy = null, $limit = null, $offset = null)
 */
class DcategoryRepository extends ServiceEntityRepository
{
    public function __construct(ManagerRegistry $registry)
    {
        parent::__construct($registry, Dcategory::class);
    }

//    /**
//     * @return Dcategory[] Returns an array of Dcategory objects
//     */
//    public function findByExampleField($value): array
//    {
//        return $this->createQueryBuilder('d')
//            ->andWhere('d.exampleField = :val')
//            ->setParameter('val', $value)
//            ->orderBy('d.id', 'ASC')
//            ->setMaxResults(10)
//            ->getQuery()
//            ->getResult()
//        ;
//    }

//    public function findOneBySomeField($value): ?Dcategory
//    {
//        return $this->createQueryBuilder('d')
//            ->andWhere('d.exampleField = :val')
//            ->setParameter('val', $value)
//            ->getQuery()
//            ->getOneOrNullResult()
//        ;
//    }
}
