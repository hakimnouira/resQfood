<?php

declare(strict_types=1);

namespace DoctrineMigrations;

use Doctrine\DBAL\Schema\Schema;
use Doctrine\Migrations\AbstractMigration;

/**
 * Auto-generated Migration: Please modify to your needs!
 */
final class Version20240324142954 extends AbstractMigration
{
    public function getDescription(): string
    {
        return '';
    }

    public function up(Schema $schema): void
    {
        // this up() migration is auto-generated, please modify it to your needs
        $this->addSql('ALTER TABLE product_history DROP FOREIGN KEY Foreign key');
        $this->addSql('DROP TABLE basket');
        $this->addSql('DROP TABLE comments');
        $this->addSql('DROP TABLE events');
        $this->addSql('DROP TABLE likes');
        $this->addSql('DROP TABLE line');
        $this->addSql('DROP TABLE notifications');
        $this->addSql('DROP TABLE participations');
        $this->addSql('DROP TABLE person');
        $this->addSql('DROP TABLE posts');
        $this->addSql('DROP TABLE product');
        $this->addSql('DROP TABLE product_history');
        $this->addSql('DROP TABLE testimony');
        $this->addSql('DROP TABLE testing');
        $this->addSql('DROP TABLE user');
        $this->addSql('ALTER TABLE dcategory MODIFY dcategory_id INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON dcategory');
        $this->addSql('ALTER TABLE dcategory CHANGE dcategory_description dcategory_description VARCHAR(255) NOT NULL, CHANGE dcategory_id id INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE dcategory ADD PRIMARY KEY (id)');
        $this->addSql('ALTER TABLE donation MODIFY donation_id INT NOT NULL');
        $this->addSql('DROP INDEX `primary` ON donation');
        $this->addSql('ALTER TABLE donation DROP udonor_id, CHANGE donation_amount donation_amount DOUBLE PRECISION NOT NULL, CHANGE food_name food_name VARCHAR(255) NOT NULL, CHANGE dcategory_id dcategory_id INT NOT NULL, CHANGE donation_id id INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE donation ADD CONSTRAINT FK_31E581A02940BD16 FOREIGN KEY (dcategory_id) REFERENCES dcategory (id)');
        $this->addSql('CREATE INDEX IDX_31E581A02940BD16 ON donation (dcategory_id)');
        $this->addSql('ALTER TABLE donation ADD PRIMARY KEY (id)');
    }

    public function down(Schema $schema): void
    {
        // this down() migration is auto-generated, please modify it to your needs
        $this->addSql('CREATE TABLE basket (basket_id INT AUTO_INCREMENT NOT NULL, basket_status VARCHAR(100) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_general_ci`, user_id INT NOT NULL, confirmation_date DATE DEFAULT \'CURRENT_TIMESTAMP\', PRIMARY KEY(basket_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE comments (comment_id INT AUTO_INCREMENT NOT NULL, post_id INT DEFAULT NULL, user_id INT DEFAULT NULL, content TEXT CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_general_ci`, created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, INDEX post_id (post_id), PRIMARY KEY(comment_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE events (id INT AUTO_INCREMENT NOT NULL, name VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, date DATE NOT NULL, time VARCHAR(20) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, location VARCHAR(100) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, capacity INT NOT NULL, status VARCHAR(50) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, description VARCHAR(300) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, image VARCHAR(500) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, users_joined INT DEFAULT 0 NOT NULL, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE likes (like_id INT AUTO_INCREMENT NOT NULL, post_id INT DEFAULT NULL, user_id INT DEFAULT NULL, reaction_type VARCHAR(255) CHARACTER SET utf8mb4 DEFAULT \'NON\' COLLATE `utf8mb4_general_ci`, created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, INDEX post_id (post_id), PRIMARY KEY(like_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE line (line_id INT AUTO_INCREMENT NOT NULL, line_quantity INT NOT NULL, product_id INT NOT NULL, basket_id INT NOT NULL, user_id INT NOT NULL, line_date DATE DEFAULT \'CURRENT_TIMESTAMP\', INDEX fk2 (user_id), INDEX product_id (product_id), INDEX fk1 (basket_id), PRIMARY KEY(line_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE notifications (id_notif INT NOT NULL, message_notif VARCHAR(255) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_general_ci`, timestamp DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE participations (idP INT AUTO_INCREMENT NOT NULL, id_event INT NOT NULL, particip_name VARCHAR(200) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, INDEX fk_ide (id_event), PRIMARY KEY(idP)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE person (id INT NOT NULL, last_name VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, first_name VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, age INT NOT NULL, status VARCHAR(50) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_general_ci`) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE posts (post_id INT AUTO_INCREMENT NOT NULL, user_id INT DEFAULT NULL, category_id INT DEFAULT NULL, title VARCHAR(100) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_general_ci`, content TEXT CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_general_ci`, created_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, updated_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, image VARCHAR(500) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_general_ci`, PRIMARY KEY(post_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE product (product_id INT AUTO_INCREMENT NOT NULL, product_name VARCHAR(100) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, quantity INT NOT NULL, expiration_date DATE NOT NULL, modified_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, version INT DEFAULT 0, PRIMARY KEY(product_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE product_history (history_id INT AUTO_INCREMENT NOT NULL, product_id INT DEFAULT NULL, product_name VARCHAR(255) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_general_ci`, quantity INT DEFAULT NULL, expiration_date DATE DEFAULT NULL, modified_at DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL, INDEX Foreign key (product_id), PRIMARY KEY(history_id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE testimony (t_id INT NOT NULL, userId INT NOT NULL, title VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, txt VARCHAR(1000) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, status VARCHAR(50) CHARACTER SET utf8mb4 DEFAULT NULL COLLATE `utf8mb4_general_ci`) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE testing (udonor_id INT NOT NULL) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('CREATE TABLE user (id INT AUTO_INCREMENT NOT NULL, fName VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, lName VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, pwd VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, email VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, phone INT NOT NULL, area VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, role VARCHAR(255) CHARACTER SET utf8mb4 NOT NULL COLLATE `utf8mb4_general_ci`, PRIMARY KEY(id)) DEFAULT CHARACTER SET utf8mb4 COLLATE `utf8mb4_general_ci` ENGINE = InnoDB COMMENT = \'\' ');
        $this->addSql('ALTER TABLE product_history ADD CONSTRAINT Foreign key FOREIGN KEY (product_id) REFERENCES product (product_id)');
        $this->addSql('ALTER TABLE dcategory MODIFY id INT NOT NULL');
        $this->addSql('DROP INDEX `PRIMARY` ON dcategory');
        $this->addSql('ALTER TABLE dcategory CHANGE dcategory_description dcategory_description VARCHAR(255) DEFAULT NULL, CHANGE id dcategory_id INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE dcategory ADD PRIMARY KEY (dcategory_id)');
        $this->addSql('ALTER TABLE donation MODIFY id INT NOT NULL');
        $this->addSql('ALTER TABLE donation DROP FOREIGN KEY FK_31E581A02940BD16');
        $this->addSql('DROP INDEX IDX_31E581A02940BD16 ON donation');
        $this->addSql('DROP INDEX `PRIMARY` ON donation');
        $this->addSql('ALTER TABLE donation ADD udonor_id INT NOT NULL, CHANGE dcategory_id dcategory_id INT DEFAULT NULL, CHANGE donation_amount donation_amount DOUBLE PRECISION DEFAULT NULL, CHANGE food_name food_name VARCHAR(255) DEFAULT NULL, CHANGE id donation_id INT AUTO_INCREMENT NOT NULL');
        $this->addSql('ALTER TABLE donation ADD PRIMARY KEY (donation_id)');
    }
}
