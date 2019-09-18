package br.jfr.simples.dao;

import java.io.Serializable;
import java.util.List;

import br.jfr.simples.model.IPersistentEntity;


public interface IGenericDAO<T extends IPersistentEntity, ID extends Serializable> {

    /**
     * Lista todos os itens, sem filtro
     *
     * @return todos os itens daquela entidade
     */
    List<T> listAll();

    /**
     * Tras apenas um objeto, filtrado pelo seu ID
     *
     * @param id o id que se deseja buscar
     * @param lock se devemos usar ou nao o lock
     * 
     * @return o objeto pesquisado
     */
    T findById(ID id, boolean lock);
    
    /**
     * Conta todos os registros da tabela
     * 
     * @return o numero de registros (rows) da tabela
     */
    Long count();

    /**
     * Salva um entidade no banco caso ela nao exista ou atualiza ela caso o 
     * objeto passado como parametro ja exista 
     *
     * @param entity a entidade a ser salva (incluida ou atualizada)
     * 
     * @return a entidade manipulada. Se ela acaba de ser  incluida entao o seu
     *         o seu ID sera setado no objeto de retorno
     */
    T save(T entity);

    /**
     * Deleta uma entidade pelo seu objeto persistente
     *
     * @param entity a entidade a ser deletada
     */
    void delete(T entity);

}
