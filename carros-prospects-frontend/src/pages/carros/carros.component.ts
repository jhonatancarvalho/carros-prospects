import { Component, OnInit } from '@angular/core';
import { PessoaPipedriveService } from '../../services/domain/pessoa-pipedrive.service';
import { PessoaPipedriveDTO } from '../../models/pessoa-pipedrive.dto';
import { CarroService } from '../../services/domain/carro.service';
import { CarroDTO } from '../../models/carro.dto';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'carros-component',
  templateUrl: './carros.component.html',
  styleUrls: ['./carros.component.css']
})
export class CarrosComponent implements OnInit {

  formGroup: FormGroup;
  token: string;
  listaPessoasPipedrive: PessoaPipedriveDTO[];
  listaCarros: CarroDTO[];
  listaCoresCarro: string[];

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

  ngOnInit() { }

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
      console.log(error);
    });
  }

  buscaCarros() {
    this.carroService.findAll()
    .subscribe(response => {
      this.listaCarros = response;
    }, error => {
      console.log(error);
    });
  }

  buscaCoresCarro() {
    this.carroService.findCores()
    .subscribe(response => {
      this.listaCoresCarro = response;
    }, error => {
      console.log(error);
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
        console.log('Salvo com sucesso!');
      }, error => {
        console.log(error);
      });
    } else {
      console.log(this.formGroup.value);
      this.carroService.update(this.formGroup.value, this.formGroup.controls.id.value)
      .subscribe(response => {
        console.log('Alterado com sucesso!');
      }, error => {
        console.log(error);
      });
    }
    this.iniciaNovoCarro();
  }

}