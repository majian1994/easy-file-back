package com.pjjlt.easyfile.moudle.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pjjlt.easyfile.moudle.entity.ProjectFileDetail;
import com.pjjlt.easyfile.moudle.mapper.ProjectFileDetailMapper;
import com.pjjlt.easyfile.moudle.service.ProjectFileDetailService;
import org.springframework.stereotype.Service;

@Service
public class ProjectFileDetailServiceImpl extends ServiceImpl<ProjectFileDetailMapper, ProjectFileDetail>
        implements ProjectFileDetailService {
}
