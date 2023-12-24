package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ValidacaoTutorComLimiteDeAdocoesTest {
    @InjectMocks
    private ValidacaoTutorComLimiteDeAdocoes validacao;
    @Mock
    private AdocaoRepository adocaoRepository;
    @Mock
    private TutorRepository tutorRepository;
    @Mock
    private SolicitacaoAdocaoDto dto;
    @Mock
    private Adocao adocao;
    @Mock
    private Tutor tutor;

    @Test
    @DisplayName("Deveria permitir solicitação de adoção do pet")
    void validaTutorLimiteAdocaoCenario01() {
        BDDMockito
                .given(adocaoRepository.findAll())
                .willReturn(List.of(new Adocao[]{ adocao }));
        BDDMockito
                .given(tutorRepository.getReferenceById(dto.idTutor()))
                .willReturn(tutor);
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(adocao.getStatus()).willReturn(StatusAdocao.APROVADO);

        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    @DisplayName("Não deveria permitir solicitação de adoção do pet")
    void validaTutorLimiteAdocaoCenario02() {
        BDDMockito
                .given(adocaoRepository.findAll())
                .willReturn(List.of(new Adocao[]{ adocao, adocao, adocao, adocao, adocao }));
        BDDMockito
                .given(tutorRepository.getReferenceById(dto.idTutor()))
                .willReturn(tutor);
        BDDMockito.given(adocao.getTutor()).willReturn(tutor);
        BDDMockito.given(adocao.getStatus()).willReturn(StatusAdocao.APROVADO);

        assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}