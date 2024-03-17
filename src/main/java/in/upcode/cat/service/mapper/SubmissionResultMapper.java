package in.upcode.cat.service.mapper;

import in.upcode.cat.domain.Submission;
import in.upcode.cat.domain.SubmissionResult;
import in.upcode.cat.service.dto.SubmissionDTO;
import in.upcode.cat.service.dto.SubmissionResultDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SubmissionResult} and its DTO {@link SubmissionResultDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubmissionResultMapper extends EntityMapper<SubmissionResultDTO, SubmissionResult> {
    @Mapping(target = "submission", source = "submission", qualifiedByName = "submissionId")
    SubmissionResultDTO toDto(SubmissionResult s);

    @Named("submissionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubmissionDTO toDtoSubmissionId(Submission submission);
}
