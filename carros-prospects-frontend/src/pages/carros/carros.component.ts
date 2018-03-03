import { Component, OnInit } from '@angular/core';
import { PessoaPipedriveService } from '../../services/domain/pessoa-pipedrive.service';
import { PessoaPipedriveDTO } from '../../models/pessoa-pipedrive.dto';
import { CarroService } from '../../services/domain/carro.service';
import { CarroDTO } from '../../models/carro.dto';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MENSAGEM } from '../../layout/message/message.constants';

@Component({
  selector: 'carros-component',
  templateUrl: './carros.component.html',
  styleUrls: ['./carros.component.css']
})
export class CarrosComponent {

  formGroup: FormGroup;
  token: string;
  listaPessoasPipedrive: PessoaPipedriveDTO[];
  listaCarros: CarroDTO[];
  listaCoresCarro: string[];
  mensagem;

  constructor(
    private pessoaPipedriveService: PessoaPipedriveService,
    private carroService: CarroService,
    private formBuilder: FormBuilder
  ) { 
    this.formGroup = this.formBuilder.group({
      id: [null, []],
      pessoaId: [null, [Validators.required]],
      modelo: ['', [Validators.required]],
      ano: ['', [Validators.required]],
      cor: [null, [Validators.required]]
    });
  }

  iniciaNovoCarro() {
    this.formGroup.reset();
    this.buscaPessoasPipedrive();
  }

  buscaPessoasPipedrive() {
    this.pessoaPipedriveService.findAll(this.token)
    .subscribe(response => {
      this.listaPessoasPipedrive = response;
      this.buscaCarros();
      this.buscaCoresCarro();
    }, error => {
      this.mostrarMensagem(MENSAGEM.ERROR, 'Problema ao buscar pessoas no Pipedrive com este token, verifique seu token');
    });
  }

  buscaCarros() {
    this.carroService.findAll()
    .subscribe(response => {
      this.listaCarros = response;
    }, error => {
      this.mostrarMensagem(MENSAGEM.ERROR, null, error);
    });
  }

  buscaCoresCarro() {
    this.carroService.findCores()
    .subscribe(response => {
      this.listaCoresCarro = response;
    }, error => {
      this.mostrarMensagem(MENSAGEM.ERROR, null, error);
    });
  }

  selecionaCarro(carro : CarroDTO) {
    this.formGroup.controls.id.setValue(carro.id);
    this.formGroup.controls.pessoaId.setValue(carro.pessoaId);
    this.formGroup.controls.modelo.setValue(carro.modelo);
    this.formGroup.controls.ano.setValue(carro.ano);
    this.formGroup.controls.cor.setValue(carro.cor);
  }

  salvarCarro() {
    if (this.formGroup.controls.id.value == null) {
      this.carroService.save(this.formGroup.value)
      .subscribe(response => {
        this.mostrarMensagem(MENSAGEM.SUCCESS, 'Salvo com sucesso!');
        this.iniciaNovoCarro();
      }, error => {
        this.mostrarMensagem(MENSAGEM.ERROR, null, error);
      });
      return;
    } 
    
    this.carroService.update(this.formGroup.value, this.formGroup.controls.id.value)
    .subscribe(response => {
      this.mostrarMensagem(MENSAGEM.SUCCESS, 'Alterado com sucesso!');
      this.iniciaNovoCarro();
    }, error => {
      this.mostrarMensagem(MENSAGEM.ERROR, null, error);
    });
  }

  excluirCarro() {
    this.carroService.delete(this.formGroup.controls.id.value)
    .subscribe(response => {
      this.mostrarMensagem(MENSAGEM.SUCCESS, 'Excluido com sucesso!');
      this.iniciaNovoCarro();
    }, error => {
      console.log(error);
      this.mostrarMensagem(MENSAGEM.ERROR, null, error);
    });
  }

  mostrarMensagem(tipo: string, texto: string, erro?, timeout?: number) {
    if (erro) {
      let mensagemErro = JSON.parse(erro.error);
      let mensagem = {
        tipo: tipo,
        titulo: mensagemErro.error,
        listaMensagens: mensagemErro.erros
      };
      this.mensagem = mensagem;
      return;
    }
    let mensagem = {
        tipo: tipo,
        texto: texto
    };
    this.mensagem = mensagem;
  }
}