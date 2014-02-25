create table DB123.msm_user ( msm_login_id INT NOT NULL AUTO_INCREMENT,
   msm_given_name VARCHAR(100) NOT NULL,
   msm_display_name VARCHAR(40) NOT NULL,
   submission_date DATE,
   PRIMARY KEY ( msm_login_id )
);

select * from DB123.msm_user


insert into DB123.msm_user values (default,"Bell","Pepp","2008-01-02")

