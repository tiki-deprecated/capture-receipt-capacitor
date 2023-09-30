import type { Account } from 'src/account';
import type { CallbackDataErrorInterface, Receipt } from 'src/types';

export type Callback = (payload?: Account | Receipt | CallbackDataErrorInterface) => void