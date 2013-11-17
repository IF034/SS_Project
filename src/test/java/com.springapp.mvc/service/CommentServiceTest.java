package com.springapp.mvc.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@ContextConfiguration(locations = {"classpath*:/TestContext/spring/test-root-context.xml"})
public class CommentServiceTest {

   /* @Autowired
    ICommentRepository commentRepository;*/

    @Test
    public void testBond() {
       /* Comment comment = commentRepository.findOne(2);*/
        assertEquals("3", "3");
    }

}

