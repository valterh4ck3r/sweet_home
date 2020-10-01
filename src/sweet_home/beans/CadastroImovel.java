package sweet_home.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.SelectEvent;

import sweet_home.Endereco;
import sweet_home.Imovel;
import sweet_home.Tipo;
import sweet_home.Usuario;
import sweet_home.servico.EnderecoServico;
import sweet_home.servico.ImovelServico;


@ManagedBean(name="Endereco")
@RequestScoped
public class CadastroImovel implements Serializable {
        
    @EJB
    private ImovelServico imovelServico; 
    private int banheiros = 0;    
    private int quartos = 0;
    private int salas = 0;
    private static Usuario usuario = null;
    private int tipo = 2;
    private String descricao = null;
    private double valor = 0.0;
    private UIComponent mybutton;
    protected static String resp = "";
        
    private String cidade = null;
    private String bairro = null;
    private String rua = null;
    private String numero = null;    
    private String CEP = null;
    private String estado = null;
    private static List<Imovel> lista = null;
    private static Endereco selected = null; 
           
    public String cadastrar() {
                
        lista = imovelServico.recuperarImoveis();
                        
        Imovel imovel = new Imovel();
        
        Endereco endereco = new Endereco();
        endereco.setNumero(numero);        
        endereco.setRua(rua);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);      
        endereco.setEstado(estado);
        endereco.setCEP(CEP);
                
        HttpSession sessao = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        Usuario usuarioLogado = (Usuario) sessao.getAttribute("logado");
                
        imovel.setQuartos(quartos);
        imovel.setBanheiros(banheiros);
        imovel.setSalas(salas);
        imovel.setTipo(tipo);
        imovel.setDescricao(descricao);
        imovel.setValor(valor);
        imovel.setEndereco(endereco);
        imovel.setUsuario(usuarioLogado);
        
        imovelServico.persistir(imovel);    
        resp = "Im�vel cadastrado com sucesso!";
        
        return "cadastro";
    }
      
     
    public void excluir(Imovel imovel) {
               
       imovelServico.remover(imovel);      
    }
     
       
    public Endereco getSelected(){
        return selected;
    }
    
    public void setSelected(Endereco selected) {
        this.selected = selected;
    }
    
    public int getBanheiros(){
        return banheiros;
    }
    
    public void setBanheiros(int banheiros) {
        this.banheiros = banheiros;
    }
    
    public int getQuartos(){
        return quartos;
    }
    
    public void setQuartos(int quartos) {
        this.quartos = quartos;
    }

    public int getSalas(){
        return salas;
    }
    
    public void setSalas(int salas) {
        this.salas = salas;
    }
    
    public String getCidade() {
        return cidade;
    }
    
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
    
    public String getBairro() {
        return bairro;
    }
    
    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
    
    public String getRua() {
        return rua;
    }
    
    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getNumero() {
        return numero;
    }
    
    public void setNumero(String numero) {
        this.numero = numero;
    }
    
    public String getCEP() {
        return CEP;
    }
    
    public void setCEP(String CEP) {
        this.CEP = CEP;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public List<Imovel> getLista() {
        lista = imovelServico.recuperarImoveis();
        return lista;
    }
        
    public void setLista() {
        lista = imovelServico.recuperarImoveis();
    }
    

    public Usuario getUsuario() {
        return usuario;
    }
    
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
          
    public int getTipo() {        
        return tipo;
    }
        
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public double getValor() {
        return valor;
    }
    
    public void setValor(double valor) {
        this.valor = valor;
    }    
    
    public UIComponent getMybutton() {
        return mybutton;
    }
    
    public void setMybutton(UIComponent mybutton) {
        this.mybutton = mybutton;
    }
    
    public String getResp() {
        return resp;
    }
    
    public void setResp(String resp) {
        this.resp = resp;
    }
    
}