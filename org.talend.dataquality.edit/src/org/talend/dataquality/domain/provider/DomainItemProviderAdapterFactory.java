/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.dataquality.domain.provider;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.edit.provider.ChangeNotifier;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IChangeNotifier;
import org.eclipse.emf.edit.provider.IDisposable;
import org.eclipse.emf.edit.provider.IEditingDomainItemProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.INotifyChangedListener;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.talend.dataquality.domain.util.DomainAdapterFactory;

/**
 * This is the factory that is used to provide the interfaces needed to support Viewers.
 * The adapters generated by this factory convert EMF adapter notifications into calls to {@link #fireNotifyChanged fireNotifyChanged}.
 * The adapters also support Eclipse property sheets.
 * Note that most of the adapters are shared among multiple instances.
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class DomainItemProviderAdapterFactory extends DomainAdapterFactory implements ComposeableAdapterFactory, IChangeNotifier, IDisposable {
    /**
     * This keeps track of the root adapter factory that delegates to this adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected ComposedAdapterFactory parentAdapterFactory;

    /**
     * This is used to implement {@link org.eclipse.emf.edit.provider.IChangeNotifier}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IChangeNotifier changeNotifier = new ChangeNotifier();

    /**
     * This keeps track of all the supported types checked by {@link #isFactoryForType isFactoryForType}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected Collection<Object> supportedTypes = new ArrayList<Object>();

    /**
     * This constructs an instance.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public DomainItemProviderAdapterFactory() {
        supportedTypes.add(IEditingDomainItemProvider.class);
        supportedTypes.add(IStructuredItemContentProvider.class);
        supportedTypes.add(ITreeItemContentProvider.class);
        supportedTypes.add(IItemLabelProvider.class);
        supportedTypes.add(IItemPropertySource.class);
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.domain.Domain} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DomainItemProvider domainItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.domain.Domain}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createDomainAdapter() {
        if (domainItemProvider == null) {
            domainItemProvider = new DomainItemProvider(this);
        }

        return domainItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.domain.EnumerationRule} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected EnumerationRuleItemProvider enumerationRuleItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.domain.EnumerationRule}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createEnumerationRuleAdapter() {
        if (enumerationRuleItemProvider == null) {
            enumerationRuleItemProvider = new EnumerationRuleItemProvider(this);
        }

        return enumerationRuleItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.domain.RangeRestriction} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RangeRestrictionItemProvider rangeRestrictionItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.domain.RangeRestriction}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createRangeRestrictionAdapter() {
        if (rangeRestrictionItemProvider == null) {
            rangeRestrictionItemProvider = new RangeRestrictionItemProvider(this);
        }

        return rangeRestrictionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.domain.LiteralValue} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LiteralValueItemProvider literalValueItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.domain.LiteralValue}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createLiteralValueAdapter() {
        if (literalValueItemProvider == null) {
            literalValueItemProvider = new LiteralValueItemProvider(this);
        }

        return literalValueItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.domain.LengthRestriction} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LengthRestrictionItemProvider lengthRestrictionItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.domain.LengthRestriction}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createLengthRestrictionAdapter() {
        if (lengthRestrictionItemProvider == null) {
            lengthRestrictionItemProvider = new LengthRestrictionItemProvider(this);
        }

        return lengthRestrictionItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.domain.NumericValue} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected NumericValueItemProvider numericValueItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.domain.NumericValue}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createNumericValueAdapter() {
        if (numericValueItemProvider == null) {
            numericValueItemProvider = new NumericValueItemProvider(this);
        }

        return numericValueItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.domain.TextValue} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected TextValueItemProvider textValueItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.domain.TextValue}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createTextValueAdapter() {
        if (textValueItemProvider == null) {
            textValueItemProvider = new TextValueItemProvider(this);
        }

        return textValueItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.domain.IntegerValue} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected IntegerValueItemProvider integerValueItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.domain.IntegerValue}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createIntegerValueAdapter() {
        if (integerValueItemProvider == null) {
            integerValueItemProvider = new IntegerValueItemProvider(this);
        }

        return integerValueItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.domain.RealNumberValue} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected RealNumberValueItemProvider realNumberValueItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.domain.RealNumberValue}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createRealNumberValueAdapter() {
        if (realNumberValueItemProvider == null) {
            realNumberValueItemProvider = new RealNumberValueItemProvider(this);
        }

        return realNumberValueItemProvider;
    }

    /**
     * This keeps track of the one adapter used for all {@link org.talend.dataquality.domain.DateValue} instances.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected DateValueItemProvider dateValueItemProvider;

    /**
     * This creates an adapter for a {@link org.talend.dataquality.domain.DateValue}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter createDateValueAdapter() {
        if (dateValueItemProvider == null) {
            dateValueItemProvider = new DateValueItemProvider(this);
        }

        return dateValueItemProvider;
    }

    /**
     * This returns the root adapter factory that contains this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public ComposeableAdapterFactory getRootAdapterFactory() {
        return parentAdapterFactory == null ? this : parentAdapterFactory.getRootAdapterFactory();
    }

    /**
     * This sets the composed adapter factory that contains this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParentAdapterFactory(ComposedAdapterFactory parentAdapterFactory) {
        this.parentAdapterFactory = parentAdapterFactory;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object type) {
        return supportedTypes.contains(type) || super.isFactoryForType(type);
    }

    /**
     * This implementation substitutes the factory itself as the key for the adapter.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Adapter adapt(Notifier notifier, Object type) {
        return super.adapt(notifier, this);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object adapt(Object object, Object type) {
        if (isFactoryForType(type)) {
            Object adapter = super.adapt(object, type);
            if (!(type instanceof Class<?>) || (((Class<?>)type).isInstance(adapter))) {
                return adapter;
            }
        }

        return null;
    }

    /**
     * This adds a listener.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void addListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.addListener(notifyChangedListener);
    }

    /**
     * This removes a listener.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void removeListener(INotifyChangedListener notifyChangedListener) {
        changeNotifier.removeListener(notifyChangedListener);
    }

    /**
     * This delegates to {@link #changeNotifier} and to {@link #parentAdapterFactory}.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void fireNotifyChanged(Notification notification) {
        changeNotifier.fireNotifyChanged(notification);

        if (parentAdapterFactory != null) {
            parentAdapterFactory.fireNotifyChanged(notification);
        }
    }

    /**
     * This disposes all of the item providers created by this factory. 
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void dispose() {
        if (domainItemProvider != null) domainItemProvider.dispose();
        if (enumerationRuleItemProvider != null) enumerationRuleItemProvider.dispose();
        if (rangeRestrictionItemProvider != null) rangeRestrictionItemProvider.dispose();
        if (literalValueItemProvider != null) literalValueItemProvider.dispose();
        if (lengthRestrictionItemProvider != null) lengthRestrictionItemProvider.dispose();
        if (numericValueItemProvider != null) numericValueItemProvider.dispose();
        if (textValueItemProvider != null) textValueItemProvider.dispose();
        if (integerValueItemProvider != null) integerValueItemProvider.dispose();
        if (realNumberValueItemProvider != null) realNumberValueItemProvider.dispose();
        if (dateValueItemProvider != null) dateValueItemProvider.dispose();
    }

}
