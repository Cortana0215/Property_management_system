package com.example.propertymanagement.repository;

import com.example.propertymanagement.entity.Notice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class NoticeRepositoryTest {

    @Autowired
    private NoticeRepository noticeRepository;

    @Test
    void testSaveAndFindById() {
        Notice notice = new Notice();
        notice.setTitle("测试公告");
        notice.setContent("这是测试内容");
        notice.setTargetRole("ALL");
        notice.setPublisherName("管理员");
        
        Notice saved = noticeRepository.save(notice);
        
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("测试公告");
    }

    @Test
    void testCount() {
        long initialCount = noticeRepository.count();
        
        Notice notice = new Notice();
        notice.setTitle("新公告");
        notice.setContent("内容");
        noticeRepository.save(notice);
        
        assertThat(noticeRepository.count()).isEqualTo(initialCount + 1);
    }

    @Test
    void testDelete() {
        Notice notice = new Notice();
        notice.setTitle("待删除公告");
        notice.setContent("内容");
        Notice saved = noticeRepository.save(notice);
        
        noticeRepository.deleteById(saved.getId());
        
        assertThat(noticeRepository.findById(saved.getId())).isEmpty();
    }
}
